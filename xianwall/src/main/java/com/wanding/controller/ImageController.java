package com.wanding.controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.wanding.config.ImageProperties;
import com.wanding.model.UserInfoCheck;
import com.wanding.service.UserInfoCheckService;



@Controller
@RequestMapping("/images")
public class ImageController {

	    private static final Logger LOGGER = LoggerFactory.getLogger(ImageController.class);
	  
	    @Autowired
	    private UserInfoCheckService userInfoCheckService;
	    @Autowired
	    private  ImageProperties  imageProperties;
	    
	    
		/**
		 * @Title: imageUpload   
		 * @Description: 图片上传
		 * @param:userId 用户id, photoType:图片类型（cardFront:身份证正面，cardBack:身份证背面，workCard:工作证明照，stuCard:学生证照）      
		 * @throws
		 */
	    @RequestMapping(value = "/imageUpload",method = RequestMethod.POST )
	    @ResponseBody
	    public Object photoUpload(Integer userId,String photoType, @RequestParam MultipartFile myfile,HttpServletResponse response) {
	    	 JSONObject result = new JSONObject();
	    	 try {
	    		 if(verifFile(myfile,result)) {
	 	 	        String imageuploadpath = imageProperties.getImageuploadpath();
	 	 	       if(!new File(imageuploadpath).exists())   {
	 		            new File(imageuploadpath).mkdirs();
	 		        }
	                String fileType = myfile.getContentType();
	 	 	        String imageType = fileType.substring(fileType.indexOf("/") + 1);;
	 		        String newImageName = userId + "_" + String.valueOf(System.currentTimeMillis()) + "." + imageType;
	 		        String path = imageuploadpath+System.getProperty("file.separator") + newImageName;
	 		        myfile.transferTo(new File(path));
	 		        int count = 0;
	 		        UserInfoCheck userInfoCheck = userInfoCheckService.findByUserId(userId);
	 		        if(userInfoCheck != null){
	 		        	handleDoc(photoType, newImageName, userInfoCheck);
	 		        	userInfoCheck.setUpdatedtime(new Date(System.currentTimeMillis()));
	 		            count = userInfoCheckService.updateUserInfoCheck(userInfoCheck);
	 		        }else {
	 		        	UserInfoCheck inertuserInfoCheck = new UserInfoCheck();
	 		        	inertuserInfoCheck.setUserid(userId);
	 		        	inertuserInfoCheck.setCreatedtime(new Date(System.currentTimeMillis()));
	 		        	inertuserInfoCheck.setStatus("0");
	 		        	handleDoc(photoType, newImageName, inertuserInfoCheck);
	 		            count =userInfoCheckService.addUserInfoCheck(inertuserInfoCheck);
	 		        }
	 		        if(count==0){
	 		    	  result.put( "success", false);
	 		    	  result.put( "message", "文件上传失败" );
		            }else {
		            	result.put( "success", true);
		            	result.put( "message", "文件上传成功");
		            	result.put( "imageName", newImageName);

		            }
	 	    	 }else {
	 	    		 return result;
	 	    	 }
	    	 }catch(Exception ex) {
	    		 ex.printStackTrace();
	             LOGGER.error(">>>>>>图片上传异常，e={}", ex.getMessage());
	    		 result.put( "success", false);
		    	 result.put( "message", "文件上传失败" );
	    	 }
                    return result;
	    }


		private void handleDoc(String photoType, String newImageName, UserInfoCheck userInfoCheck) {
			if("cardFront".equals(photoType)){
				userInfoCheck.setIdcardfrontdoc(newImageName);
			}else if("cardBack".equals(photoType)) {
				userInfoCheck.setIdcardbackdoc(newImageName);
			}else if("workCard".equals(photoType)) {
				userInfoCheck.setAttachdoc(newImageName);
				userInfoCheck.setAttachtype(photoType);
			}else if("stuCard".equals(photoType)) {
				userInfoCheck.setIdcardfrontdoc(newImageName);
				userInfoCheck.setAttachtype(photoType);
			}
		}


	    private boolean verifFile(MultipartFile myfile,JSONObject result) {
            String[] IMAGE_TYPE = imageProperties.getImageType().split(",");
	    	if (myfile.isEmpty()) {
	    		result.put( "success", false);
	    		result.put( "message", "上传文件不能为空");
		        return false;
	    	 }
            boolean flag = false;
   	        for (String type : IMAGE_TYPE) {
                if (StringUtils.endsWithIgnoreCase(myfile.getOriginalFilename(), type)) {
                    flag = true;
                    break;
                }
            }
	   	    if (!flag) {
		        result.put( "success", false);
	   	    	result.put("message", "图片格式不正确,支持png|jpg|jpeg");
	   	    	return false;
	   	    }
	   	    if(myfile.getSize()>imageProperties.getImageSize()) {
	   	    	result.put( "success", false);
	   	    	result.put("message", "图片大小不能超过4M");
	   	    	return false;
	   	    	
	   	    }
            return true;
		}


	    @RequestMapping(value = "/readImage",method = RequestMethod.GET)
	    public  void show(String fileName,HttpServletRequest request,HttpServletResponse response){
	 	        String imageuploadpath = imageProperties.getImageuploadpath();
	        File file=new File(imageuploadpath+System.getProperty("file.separator")+fileName);
	        if(file.exists()&&!"".equals(fileName)){
	            try {
	                DataOutputStream dos=new DataOutputStream(response.getOutputStream());
	                DataInputStream dis=new DataInputStream(new FileInputStream(file.getAbsolutePath()));
	                byte[] data=new byte[2048];
	                while((dis.read(data))!=-1){
	                    dos.write(data);
	                    dos.flush();
	                }
	                dis.close();
	                dos.close();
	            } catch (IOException e) {
	            	 e.printStackTrace();
		             LOGGER.error(">>>>>>图片读取异常，e={}", e.getMessage());
	            }
	        }
	    }
}
