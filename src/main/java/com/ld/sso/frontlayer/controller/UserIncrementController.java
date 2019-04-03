package com.ld.sso.frontlayer.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ld.sso.crm.domain.CRMCustmemberModel;
import com.ld.sso.crm.properties.CRMInterfaceProperties;
import com.ld.sso.crm.service.ICRMInterfaceService;
import com.ld.sso.crm.util.CRMCharacterConverter;
import com.ld.sso.frontlayer.databean.CommonRequestParam;
import com.ld.sso.frontlayer.databean.CommonResponseInfo;
import com.ld.sso.frontlayer.util.SignVerification;
import com.ld.sso.midlayer.service.IuserInfoService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "RestAPI")
public class UserIncrementController {
    Logger logger = LogManager.getLogger(this.getClass());

    @Resource
	private IuserInfoService userInfoService;
    
    @Resource
    private ICRMInterfaceService crmInterfaceService;

    @Autowired
    private CRMInterfaceProperties crmInterfaceProperties;

    /**
     * 返回某个日期之后变动过的用户列表
     *
     * @param param updateTime：用于判断的日期参数
     *              pageNo/pageSize: 分页参数
     * @return 用户信息列表
     */
    @RequestMapping(value = "/usersAfterUpdate", method = {RequestMethod.POST},
            produces = "application/json; charset=utf-8")
    public String userIncrementUsersByUpdate(@RequestBody CommonRequestParam param) {

    	CommonResponseInfo response = new CommonResponseInfo();
    	
        logger.warn("~~~usersAfterUpdate()~~~~param:{}", JSONArray.toJSON(param));
        // 验证签名
        if (this.signVerification(param)) {
        	response =  this.queryUserInfoListByUpdateTime(param);
        } else {
            response.setCode("9007");
            response.setMsg("数据的签名信息错误,属于非法访问");
            logger.warn("~~~userIncrementController()~~~~数据的签名信息错误,属于非法访问");
            
        }
        
        //return response;
        //为null的不展示
        return JSONObject.toJSONString(response);  
    }

    public boolean signVerification(CommonRequestParam param) {
        logger.info("~~~signVerification()~~~~");
        String sign = SignVerification.getSha1(param.getTimestamp() + crmInterfaceProperties.getSignKey());

        return sign.equals(param.getSign());
    }

    public CommonResponseInfo queryUserInfoListByUpdateTime(CommonRequestParam param) {
        Map<String, Object> params = param.getParams();

        Converter<Integer> intConverter = object -> Integer.parseInt(String.valueOf(object));
        Converter<Long> longConverter = object -> Long.parseLong(String.valueOf(object));

        CommonResponseInfo info = new CommonResponseInfo();
        Long date;
        int pageNo;
        int pageSize;
        try {
            date = checkParams("updateTime", longConverter, params, null);
            pageNo = checkParams("pageNo", intConverter, params, 1);
            pageSize = checkParams("pageSize", intConverter, params, 10);
        } catch (RuntimeException e) {
            info.setCode("9004");
            info.setMsg("无效参数或不符合JSON格式规范：" + e.toString());
            return info;
        }

        if (pageSize > 30) {
            pageSize = 30;
        }

        Integer totalCount = crmInterfaceService.getMemberCountAfterMaintainDate(date);
        List<CRMCustmemberModel> userInfo = null;
        if(totalCount > 0){
        	 int startRow = (pageNo - 1) * pageSize + 1;
             userInfo = crmInterfaceService.getMemberSelectedInfoAfterMaintainDate(
                     date, startRow, pageSize
             );

             if (userInfo != null && userInfo.size() > 0) {
                 for (CRMCustmemberModel model : userInfo) {
                     model.setCmname(null != model.getCmname() ? CRMCharacterConverter.convert8859P1ToGBK(model.getCmname()) : null);
                     if(null != model.getCustypeModel()
                    	 && !StringUtils.isEmpty(model.getCustypeModel().getCtname())){
                    	 model.getCustypeModel().setCtname(CRMCharacterConverter.convert8859P1ToGBK(model.getCustypeModel().getCtname()));
                     }
                 }
             }
        }
        
        Map resultData = new HashMap();
        resultData.put("userInfoList", userInfo);
        resultData.put("totalCount", totalCount);
        resultData.put("totalPage", totalCount/pageSize + 1);
        resultData.put("pageNo", pageNo);
        resultData.put("pageSize", pageSize);
        
        info.setCode("0");
        info.setData(resultData);
        return info;
    }

    @RequestMapping(value = "/getJFBalanceByCustId", method = { RequestMethod.POST }, 
			produces = "application/json; charset=utf-8")
	public CommonResponseInfo userInfoManager(@RequestBody CommonRequestParam param) {
		
		if(null != param && null != param.getParams()
				&& null != param.getParams().get("mobile") 
				&& StringUtil.isNotEmpty(param.getParams().get("mobile").toString())
				&& null != param.getParams().get("cmcustid") 
				&& StringUtil.isNotEmpty(param.getParams().get("cmcustid").toString())){
		
			return userInfoService.queryJFBalanceByCMcustid(param.getParams().get("mobile").toString(), param.getParams().get("cmcustid").toString());
		}
		
		CommonResponseInfo response = new CommonResponseInfo();
		response.setCode("9004");
		response.setMsg("无效参数或不符合JSON格式规范");
		return response;
	}

    interface Converter<T> {
        T convert(Object object);
    }

    /**
     * 检查参数是否存在，类型是否合法
     *
     * @param paramKey     参数 key
     * @param converter    参数类型转换器
     * @param params       参数容器
     * @param <T>          参数类型
     * @param defaultValue 默认值，没有传 null
     * @return 检查后参数
     * @throws RuntimeException 参数不存在或者转换报错后抛出
     */
    private <T> T checkParams(String paramKey,
                              Converter<T> converter,
                              Map<String, Object> params,
                              T defaultValue) throws RuntimeException {
        if (params == null) {
            throw new RuntimeException(String.format("请求缺少 %s 字段", paramKey));
        }

        Object param = params.get(paramKey);

        if (param == null) {
            if (defaultValue == null) {
                throw new RuntimeException(String.format("请求缺少 %s 字段", paramKey));
            }
            return defaultValue;
        }

        try {
            return converter.convert(param);
        } catch (Exception e) {
            throw new RuntimeException(String.format("字段 %s 格式错误", paramKey));
        }
    }
}
