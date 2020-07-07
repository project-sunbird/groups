package org.sunbird.service;

import java.util.List;
import java.util.Map;
import org.sunbird.exception.BaseException;
import org.sunbird.models.Group;
import org.sunbird.models.GroupResponse;
import org.sunbird.response.Response;

public interface GroupService {

  String createGroup(Group groupObj) throws BaseException;

  Response readGroup(String groupId) throws BaseException;

  List<GroupResponse> searchGroup(Map<String, Object> searchFilter) throws BaseException;
}
