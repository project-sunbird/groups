package org.sunbird.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sunbird.response.Response;
import org.sunbird.service.UserService;
import org.sunbird.service.impl.UserServiceImpl;

public class CacheUtil {

  private static Logger logger = LoggerFactory.getLogger(CacheUtil.class);
  private static Map<String, String> configSettings = new HashMap<>();

  private static UserService userService = UserServiceImpl.getInstance();

  public static void init() {
    cacheSystemSettingConfig();
  }

  private static void cacheSystemSettingConfig() {
    Response response = userService.getSystemSettings();
    logger.info(
        "DataCacheHandler:cacheSystemConfig: Cache system setting fields" + response.getResult(),
        LoggerEnum.INFO.name());
    List<Map<String, Object>> responseList =
        (List<Map<String, Object>>) response.get(JsonKey.RESPONSE);
    if (null != responseList && !responseList.isEmpty()) {
      for (Map<String, Object> resultMap : responseList) {
        configSettings.put(
            ((String) resultMap.get(JsonKey.FIELD)), (String) resultMap.get(JsonKey.VALUE));
      }
    }
  }

  public static Map<String, String> getConfigSettings() {
    return configSettings;
  }
}
