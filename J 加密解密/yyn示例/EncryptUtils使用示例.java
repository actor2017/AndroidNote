
public void encryptTest() {
	//加密
	String encodePhone = EncryptUtils.encode3DES(phone);

	String key = "ab1cd!efi9jd,uid=*9eopO";
	String encodePhone = EncryptUtils.encode3DES(phone, key);
	
	
	//解密
	String phonebind = EncryptUtils.decode3DesECB(phonebind);
}
