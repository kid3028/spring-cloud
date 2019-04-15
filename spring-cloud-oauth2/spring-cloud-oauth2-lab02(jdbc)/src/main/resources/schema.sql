
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ClientDetails
-- ----------------------------
DROP TABLE IF EXISTS `ClientDetails`;
CREATE TABLE `ClientDetails` (
  `appId` varchar(256) NOT NULL,
  `resourceIds` varchar(256) DEFAULT NULL,
  `appSecret` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `grantTypes` varchar(256) DEFAULT NULL,
  `redirectUrl` varchar(256) DEFAULT NULL,
  `authorities` varchar(256) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additionalInformation` varchar(4096) DEFAULT NULL,
  `autoApproveScopes` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`appId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oauth_access_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_access_token`;
CREATE TABLE `oauth_access_token` (
  `token_id` varchar(256) DEFAULT NULL COMMENT 'MD5加密的access_token值',
  `token` blob COMMENT 'OAuth2AccessToken.java对象序列化后的二进制数据',
  `authentication_id` varchar(256) NOT NULL COMMENT 'MD5加密的username, client_id, scope',
  `user_name` varchar(256) DEFAULT NULL COMMENT '登录的用户名',
  `client_id` varchar(256) DEFAULT NULL COMMENT '客户端id',
  `authentication` blob COMMENT 'OAuth2Authentication.java对象序列化后的二进制数据',
  `refresh_token` varchar(256) DEFAULT NULL COMMENT 'MD5加密的refresh_token值',
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oauth_approvals
-- ----------------------------
DROP TABLE IF EXISTS `oauth_approvals`;
CREATE TABLE `oauth_approvals` (
  `userId` varchar(255) DEFAULT '' COMMENT '登录的用户名',
  `clientId` varchar(255) DEFAULT NULL COMMENT '客户端id',
  `scope` varchar(255) DEFAULT NULL COMMENT '申请的权限',
  `status` varchar(10) DEFAULT NULL COMMENT '状态(Approve/Deny)',
  `expiresAt` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '过期时间',
  `lastModifiedAt` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT 'lastModifiedAt'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details` (
  `client_id` varchar(256) NOT NULL COMMENT '客户端id',
  `resource_ids` varchar(256) DEFAULT NULL COMMENT '资源id集合，多个资源时使用逗号f分隔',
  `client_secret` varchar(256) DEFAULT NULL COMMENT '客户端秘钥',
  `scope` varchar(256) DEFAULT NULL COMMENT '客户端盛情的权限范围',
  `authorized_grant_types` varchar(256) DEFAULT NULL COMMENT '客户端支持的grant_type',
  `web_server_redirect_uri` varchar(256) DEFAULT NULL COMMENT '重定向uri',
  `authorities` varchar(256) DEFAULT NULL COMMENT '客户端所拥有的spring security的权限值，多个使用逗号分隔',
  `access_token_validity` int(11) DEFAULT NULL COMMENT 'f访问令牌有效s时间值,单位秒',
  `refresh_token_validity` int(11) DEFAULT NULL COMMENT '更新令牌有效时间值,单位秒',
  `additional_information` varchar(4096) DEFAULT NULL COMMENT '预留字段',
  `autoapprove` varchar(256) DEFAULT NULL COMMENT '用户是否自动approval操作',
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oauth_client_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_token`;
CREATE TABLE `oauth_client_token` (
  `token_id` varchar(256) DEFAULT NULL COMMENT 'MD5加密的access_token值',
  `token` blob COMMENT 'OAuth2AccessToken.javad对象序列化后的二进制数据',
  `authentication_id` varchar(256) NOT NULL COMMENT 'MD5加密的username,client_id,scope',
  `user_name` varchar(256) DEFAULT NULL COMMENT '登录的用户名',
  `client_id` varchar(256) DEFAULT NULL COMMENT '客户端Id',
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oauth_code
-- ----------------------------
DROP TABLE IF EXISTS `oauth_code`;
CREATE TABLE `oauth_code` (
  `code` varchar(256) DEFAULT NULL COMMENT '授权码未MD5加密',
  `authentication` blob COMMENT 'AuthorizationRequestHolder.java对象序列化后的二进制数据'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oauth_refresh_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_refresh_token`;
CREATE TABLE `oauth_refresh_token` (
  `token_id` varchar(256) DEFAULT NULL COMMENT 'MD5加密的refresh_token的值',
  `token` blob COMMENT 'OAuth2RefreshToken.java对象序列化后的二进制数据',
  `authentication` blob COMMENT 'OAuth2Authentication.java对象序列化后的二进制数据'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
