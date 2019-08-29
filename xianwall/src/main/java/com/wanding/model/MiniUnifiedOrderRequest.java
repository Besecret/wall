package com.wanding.model;

public class MiniUnifiedOrderRequest {

	/**
	 * 商户号
	 */
	private String mch_no;

	/**
	 * 终端号
	 */
	private String term_no;

	/**
	 * 随机字符串
	 */
	private String nonce_str;

	/**
	 * 签名
	 */
	private String sign;

	/**
	 * 签名类型
	 */
	private String sign_type;

	/**
	 * 商品描述
	 */
	private String body;

	/**
	 * 商户订单号
	 */
	private String out_trade_no;

	/**
	 * 总金额
	 */
	private String total_fee;

	/**
	 * 通知地址
	 */
	private String notify_url;

	/**
	 * 交易类型
	 */
	private String trade_type;

	/**
	 * 用户标识
	 */
	private String openid;

	/**
	 * 支付方式
	 */
	private String payMode;

	private String appid;

	public String getMch_no() {
		return mch_no;
	}

	public void setMch_no(String mch_no) {
		this.mch_no = mch_no;
	}

	public String getTerm_no() {
		return term_no;
	}

	public void setTerm_no(String term_no) {
		this.term_no = term_no;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSign_type() {
		return sign_type;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MiniUnifiedOrderRequest [mch_no=");
		builder.append(mch_no);
		builder.append(", term_no=");
		builder.append(term_no);
		builder.append(", nonce_str=");
		builder.append(nonce_str);
		builder.append(", sign=");
		builder.append(sign);
		builder.append(", sign_type=");
		builder.append(sign_type);
		builder.append(", body=");
		builder.append(body);
		builder.append(", out_trade_no=");
		builder.append(out_trade_no);
		builder.append(", total_fee=");
		builder.append(total_fee);
		builder.append(", notify_url=");
		builder.append(notify_url);
		builder.append(", trade_type=");
		builder.append(trade_type);
		builder.append(", openid=");
		builder.append(openid);
		builder.append(", payMode=");
		builder.append(payMode);
		builder.append("]");
		return builder.toString();
	}

}
