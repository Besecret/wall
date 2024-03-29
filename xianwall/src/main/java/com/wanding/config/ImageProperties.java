package com.wanding.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="image")
@PropertySource("classpath:image.properties")
public class ImageProperties {


   

    private String imageuploadpath; //保存路径
    
    private String imagereadpath;
    
    private String imageType; //图片类型
    
    private Long imageSize;

	public String getImageuploadpath() {
		return imageuploadpath;
	}

	public void setImageuploadpath(String imageuploadpath) {
		this.imageuploadpath = imageuploadpath;
	}

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	public Long getImageSize() {
		return imageSize;
	}

	public void setImageSize(Long imageSize) {
		this.imageSize = imageSize;
	}

	public String getImagereadpath() {
		return imagereadpath;
	}

	public void setImagereadpath(String imagereadpath) {
		this.imagereadpath = imagereadpath;
	}
    
	
	

}
