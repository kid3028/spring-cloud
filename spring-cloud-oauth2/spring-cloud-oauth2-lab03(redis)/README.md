### JWT非对称加密
#### 生成加密文件
``` sbtshell
qull@qull:~/develop/github$   keytool -genkeypair -alias mytest -keyalg RSA -keypass mypass -keystore mytest.jks -storepass mypass
Picked up _JAVA_OPTIONS:   -Dawt.useSystemAAFontSettings=gasp
您的名字与姓氏是什么?
  [Unknown]:  kui
您的组织单位名称是什么?
  [Unknown]:  kui
您的组织名称是什么?
  [Unknown]:  kui
您所在的城市或区域名称是什么?
  [Unknown]:  kui
您所在的省/市/自治区名称是什么?
  [Unknown]:  kui
该单位的双字母国家/地区代码是什么?
  [Unknown]:  kui
CN=kui, OU=kui, O=kui, L=kui, ST=kui, C=kui是否正确?
  [否]:  y


Warning:
JKS 密钥库使用专用格式。建议使用 "keytool -importkeystore -srckeystore mytest.jks -destkeystore mytest.jks -deststoretype pkcs12" 迁移到行业标准格式 PKCS12。

```


#### 导出公钥

``` sbtshell

qull@qull:~/develop/github$ keytool -list -rfc --keystore mytest.jks | openssl x509 -inform pem -pubkey
Picked up _JAVA_OPTIONS:   -Dawt.useSystemAAFontSettings=gasp
输入密钥库口令:  mypass

Warning:
JKS 密钥库使用专用格式。建议使用 "keytool -importkeystore -srckeystore mytest.jks -destkeystore mytest.jks -deststoretype pkcs12" 迁移到行业标准格式 PKCS12。
-----BEGIN PUBLIC KEY-----
MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzpFWg79ggGMJLJNsJXGi
NIa/QJmzSk3o+7C9F/+rKSQ+LbJ5IMxGC3bcZk5murzMVYpXHUeEy5rffO4/kgUZ
27EnCFnJo3tFpfMtL4f4ITVIyIaripbtJAnxUpliN1YPqfWLNl/EmH7fYraIoOQ3
ngpFAKBeiPBPTRGS8U4azWvLwclQ89NOQhtTbBhl1tOT2Gci+3T0Ll0HYyQE9tZM
gUme6nZKU/ExWl0qSvye5h8rvQgw7Ji9HcL4f4VK3SAsqL7BTJUhO+M5n7Z6RObo
qB/ewx1RDUoNY5/kCTEQuBDQRCFwbOptY8iC8KrVAbnrpD6pxi1Znnx5cMnhIQbk
eQIDAQAB
-----END PUBLIC KEY-----
-----BEGIN CERTIFICATE-----
MIIDRzCCAi+gAwIBAgIEIqxM1zANBgkqhkiG9w0BAQsFADBUMQwwCgYDVQQGEwNr
dWkxDDAKBgNVBAgTA2t1aTEMMAoGA1UEBxMDa3VpMQwwCgYDVQQKEwNrdWkxDDAK
BgNVBAsTA2t1aTEMMAoGA1UEAxMDa3VpMB4XDTE5MDQxMjA5MzA0OFoXDTE5MDcx
MTA5MzA0OFowVDEMMAoGA1UEBhMDa3VpMQwwCgYDVQQIEwNrdWkxDDAKBgNVBAcT
A2t1aTEMMAoGA1UEChMDa3VpMQwwCgYDVQQLEwNrdWkxDDAKBgNVBAMTA2t1aTCC
ASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAM6RVoO/YIBjCSyTbCVxojSG
v0CZs0pN6PuwvRf/qykkPi2yeSDMRgt23GZOZrq8zFWKVx1HhMua33zuP5IFGdux
JwhZyaN7RaXzLS+H+CE1SMiGq4qW7SQJ8VKZYjdWD6n1izZfxJh+32K2iKDkN54K
RQCgXojwT00RkvFOGs1ry8HJUPPTTkIbU2wYZdbTk9hnIvt09C5dB2MkBPbWTIFJ
nup2SlPxMVpdKkr8nuYfK70IMOyYvR3C+H+FSt0gLKi+wUyVITvjOZ+2ekTm6Kgf
3sMdUQ1KDWOf5AkxELgQ0EQhcGzqbWPIgvCq1QG566Q+qcYtWZ58eXDJ4SEG5HkC
AwEAAaMhMB8wHQYDVR0OBBYEFIC48FFxrCcVQr8/VdJxC7ND9AB0MA0GCSqGSIb3
DQEBCwUAA4IBAQCESrK3hLe9eI0cdo1X3NVhlV7pRUXaERefsloQP5SQiWHzQR3O
juXCY1ARmQ90hUTYHbV9/wFdsRLVwtKHd2oo7NHLecbBIZymX+/fjr4x0EqDCAld
lHF912Y4bFNjSHj8FY8dFYFm5lbv2k+Zsgtf2uhrny7YOx4zgmS2pnYgJ37jxtNv
5fOHP9dujeyNwFvDOBD2cagHqYFZYCc5WE8FhPrYIv9kX7fKq2nx8l9CPtBqVOhf
4gaApiQkWAq7hwH4gGEBp/55rL/QFwPo+x+anb0WRzb5Ey8At0YgE9AE9oo9oWIR
ABLKznYDzlfwf1AzngQuu2qWK1Jngt0qzYnm
-----END CERTIFICATE-----


```
