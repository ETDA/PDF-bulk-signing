# AppSign
- โปรแกรมลงนามเอกสาร โดยใช้ eToken
## Prerequisites
- Java JDK 8 
- Eclipse (Editor สำหรับใช้การพัฒนา)
## How to
### Input
- PDF ตั้งต้น
- eToken ที่มี Certificate 
### Output
- PDF ที่ถูกลงนาม
### ทดสอบ
- download folder <a href="https://github.com/ETDA/PDF-bulk-signing/tree/main/demojar" target="_blank">demojar</a>
- ใช้ appBulkSign.jar สำหรับทดสอบ โดยจะมี config อยู่ใน resources 
## Function
- สามารถลงนามหลายเอกสาร
- สามารถลงนามเอกสาร 
    - มีรูปลายมือชื่อ
        ต้องแก้ไขไฟล์ "temp.xml"
        
        ```sh
        <?xml version="1.0" encoding="UTF-8"?>
        <temp>
	        <pathGraphicSignature>signature.jpg</pathGraphicSignature>
	        <x>40</x>
	        <y>285</y>
	        <height>35</height>
	        <width>116</width>
	        <pageNumber>1</pageNumber>
        </temp>
        ```
        | Parameter | Description |
        | ------ | ------ |
        | pathGraphicSignature | Path ของไฟล์รูป |
        | x | ตำแหน่งแกน x ของรูปลายมือชื่อ |
        | y | ตำแหน่งแกน y ของรูปลายมือชื่อ |
        | height | ความสูงของรูปลายมือชื่อ |
        | width | ความกว้างของรูปลายมือชื่อ |
        | pageNumber | หน้าที่ให้ลงลายมือชื่อ |
    - ไม่มีรูปลายมือชื่อ
## Base Config 
ไฟล์ app2_config_sample.xml
```sh
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
	<tsa>
		<timeStampType>computer_clock</timeStampType>
		<url>-</url>
		<tsaAuthenticationType>-</tsaAuthenticationType>
		<tsaCertificateType>
			<tsaPKCS12File>-</tsaPKCS12File>
			<tsaPKCS12Password>-</tsaPKCS12Password>
		</tsaCertificateType>			
		<tsaUesrPassword>
			<tsaUsername>-</tsaUsername>
			<tsaPassword>-</tsaPassword>
		</tsaUesrPassword>
	</tsa>
	<pkcs11>
		<name>etoken</name>
		<slot>0</slot>
		<driver>C:/windows/system32/eTPKCS11.dll</driver>
		<pkcs11SearchKeyword>CN=xxx</pkcs11SearchKeyword>
	</pkcs11>
	<sign>
		<location>TH</location>
		<reason>For Sign</reason>
		<signatureLevel>Apporoval</signatureLevel>
		<signaturePattern>GRAPHIC</signaturePattern>
		<digestAlgorithm>SHA256</digestAlgorithm>		
	</sign>
</configuration>
```
| No.   | Parameter | Description |
|-------| ------ | ------ |
| 1.    | tsa | หมวด config tsa |
| 1.1   | timeStampType | ประเภทของ TSA โดยแบ่งเป็น TSA(ผู้ให้บริการ tsa) กับ computer_clock(ใช้เวลาเครื่อง) |
| 1.2   | url | url สำหรับเรียก TSA |
| 1.3   | tsaAuthenticationType | ประเภทการเข้าใช้ <br> - "NO_AUTHENTICATION" (กรณีไม่มีการ login) <br>- "USERNAME_PASSWORD" (กรณีใช้รูปแบบ user,password )<br> - "CERTIFICATE" (รูปแบบ certificate ในการ login)|
| 1.4   | tsaCertificateType | สำหรับ tsaAuthenticationType "CERTIFICATE" |
| 1.4.1 | tsaPKCS12File | Path ไฟล์ PKCS12 |
| 1.4.2 | tsaPKCS12Password | รหัสของ PKCS12 |
| 1.5   | tsaUesrPassword | สำหรับ tsaAuthenticationType "USERNAME_PASSWORD" |
| 1.5.1 | tsaUsername | username สำหรับ login |
| 1.5.2 | tsaPassword | รหัสผ่าน |
| 2.    | pkcs11 | config token |
| 2.1   | name | ชื่อ token |
| 2.2   | slot | slot ของ token โดยปกติจะเป็น 0,1 |
| 2.3   | driver | path ของ driver token เพื่อเชื่อมต่อ token |
| 2.4   | pkcs11SearchKeyword | ชื่อ keyword ในการค้นหา certificate ที่ใช้ลงนาม |
| 3.    | sign | config การลงนาม |
| 3.1   | location | สถานที่ทำการลงนาม |
| 3.2   | reason | เหตุผลในการลงนาม |
| 3.3   | signatureLevel | กำหนดระดับของการ Sign <br>- APPROVAL<br>- NO_CHANGE<br>- FORM_FILLING<br>- FORM FILLING AND ANNOTATION |
| 3.4   | signaturePattern | กำหนด signature graphic<br>- DESCRIPTION<br>- NAME_AND_DESCRIPTION<br>- GRAPHIC_AND_DESCRIPTION<br>- GRAPHIC  |
| 3.5   | digestAlgorithm | กำหนด hash function<br>- SHA256<br>- SHA384<br>- SHA512 |
