package org.sunbird.actors;

import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.sunbird.actor.core.ActorConfig;
import org.sunbird.cache.impl.RedisCache;
import org.sunbird.request.Request;
import org.sunbird.util.JsonKey;

@ActorConfig(
  tasks = {"setCache"},
  dispatcher = "",
  asyncTasks = {}
)
public class CacheActor extends BaseActor {

  private int ttl =
      StringUtils.isNotEmpty(System.getenv("groups_redis_ttl"))
          ? Integer.parseInt(System.getenv("groups_redis_ttl"))
          : 3600000;

  @Override
  public void onReceive(Request request) throws Throwable {
    String operation = request.getOperation();
    switch (operation) {
      case "setCache":
        setCache(request);
        break;
      default:
        onReceiveUnsupportedMessage("CacheActor");
    }
  }

  private void setCache(Request request) {
    Map<String, Object> req = request.getRequest();
    RedisCache.set((String) req.get(JsonKey.KEY), (String) req.get(JsonKey.VALUE), ttl);
  }

  // TODO getCache: hari
}
