package org.sunbird.util;

import akka.actor.ActorRef;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.sunbird.Application;
import org.sunbird.models.ActorOperations;
import org.sunbird.models.GroupResponse;
import org.sunbird.request.Request;

public class GroupUtil {

  /**
   * Update Role details in the group of a user
   *
   * @param groups
   * @param groupRoleMap
   */
  public static void updateRoles(List<GroupResponse> groups, Map<String, String> groupRoleMap) {
    if (!groups.isEmpty()) {
      for (GroupResponse group : groups) {
        group.setMemberRole(groupRoleMap.get(group.getId()));
      }
    }
  }

  public static String convertTimestampToUTC(long timeInMs) {
    Date date = new Date(timeInMs);
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSSZ");
    simpleDateFormat.setLenient(false);
    return simpleDateFormat.format(date);
  }

  public static String convertDateToUTC(Object date) throws ParseException {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSSZ");
    String formattedDate = null;
    if (date instanceof Date) {
      simpleDateFormat.setLenient(false);
      formattedDate = simpleDateFormat.format((Date) date);
    } else if (date instanceof String) {
      formattedDate = simpleDateFormat.format(simpleDateFormat.parse((String) date));
    }
    return formattedDate;
  }

  public static Map<SearchServiceUtil, Map<String, String>> groupActivityIdsBySearchUtilClass(
      List<Map<String, Object>> activities) {
    Map<SearchServiceUtil, Map<String, String>> idClassTypeMap = new HashMap<>();
    for (Map<String, Object> activity : activities) {
      SearchServiceUtil searchUtil =
          ActivityConfigReader.getServiceUtilClassName((String) activity.get(JsonKey.TYPE));
      if (null != searchUtil) {
        if (idClassTypeMap.containsKey(searchUtil)) {
          Map<String, String> idActivityMap = idClassTypeMap.get(searchUtil);
          idActivityMap.put((String) activity.get(JsonKey.ID), (String) activity.get(JsonKey.TYPE));
        } else {
          Map<String, String> idActivityMap = new HashMap<>();
          idActivityMap.put((String) activity.get(JsonKey.ID), (String) activity.get(JsonKey.TYPE));
          idClassTypeMap.put(searchUtil, idActivityMap);
        }
      }
    }
    return idClassTypeMap;
  }

  /**
   * to call set cache actor
   *
   * @param key
   * @param value
   */
  public static void setCache(String key, String value) {
    Request req = new Request();
    req.setOperation(ActorOperations.SET_CACHE.getValue());
    req.getRequest().put(JsonKey.KEY, key);
    req.getRequest().put(JsonKey.VALUE, value);
    Application.getInstance()
        .getActorRef(ActorOperations.SET_CACHE.getValue())
        .tell(req, ActorRef.noSender());
  }
}
