package org.sunbird.actors;

import java.util.*;
import org.apache.commons.lang3.StringUtils;
import org.sunbird.actor.core.ActorConfig;
import org.sunbird.message.ResponseCode;
import org.sunbird.models.GroupResponse;
import org.sunbird.models.MemberResponse;
import org.sunbird.request.Request;
import org.sunbird.response.Response;
import org.sunbird.service.GroupService;
import org.sunbird.service.GroupServiceImpl;
import org.sunbird.util.CacheUtil;
import org.sunbird.util.JsonKey;
import org.sunbird.util.JsonUtils;

@ActorConfig(
  tasks = {"readGroup"},
  asyncTasks = {}
)
public class ReadGroupActor extends BaseActor {

  private GroupService groupService = new GroupServiceImpl();
  private CacheUtil cacheUtil = new CacheUtil();

  @Override
  public void onReceive(Request request) throws Throwable {
    String operation = request.getOperation();
    switch (operation) {
      case "readGroup":
        readGroup(request);
        break;
      default:
        onReceiveUnsupportedMessage("ReadGroupActor");
    }
  }
  /**
   * This method will read group in cassandra based on group id.
   *
   * @param actorMessage
   */
  private void readGroup(Request actorMessage) throws Exception {
    logger.info("ReadGroup method call");
    String groupId = (String) actorMessage.getRequest().get(JsonKey.GROUP_ID);
    List<String> requestFields = (List<String>) actorMessage.getRequest().get(JsonKey.FIELDS);
    GroupResponse groupResponse;
    String groupInfo = cacheUtil.getCache(groupId);
    if (StringUtils.isNotEmpty(groupInfo)) {
      groupResponse = JsonUtils.deserialize(groupInfo, GroupResponse.class);
    } else {
      groupResponse = groupService.readGroup(groupId);
      cacheUtil.setCache(groupId, JsonUtils.serialize(groupResponse), self());
    }
    if (requestFields.contains(JsonKey.MEMBERS)) {
      String groupMember = cacheUtil.getCache(groupId + "_" + JsonKey.MEMBERS);
      List<MemberResponse> memberResponses = new ArrayList<>();
      if (StringUtils.isNotEmpty(groupMember)) {
        memberResponses = JsonUtils.deserialize(groupMember, memberResponses.getClass());
      } else {
        memberResponses = groupService.readGroupMembers(groupId);
        cacheUtil.setCache(
            groupId + "_" + JsonKey.MEMBERS, JsonUtils.serialize(memberResponses), self());
      }
      groupResponse.setMembers(memberResponses);
    }
    if (!requestFields.contains(JsonKey.ACTIVITIES)) {
      groupResponse.setActivities(null);
    }
    Response response = new Response(ResponseCode.OK.getCode());
    Map<String, Object> map = JsonUtils.convert(groupResponse, Map.class);
    response.putAll(map);
    sender().tell(response, self());
  }
}
