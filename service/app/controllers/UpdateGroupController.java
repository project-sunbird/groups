package controllers;

import org.apache.commons.lang3.StringUtils;
import org.sunbird.exception.BaseException;
import org.sunbird.message.ResponseCode;
import org.sunbird.models.ActorOperations;
import org.sunbird.request.Request;
import play.mvc.Http;
import play.mvc.Result;
import utils.module.PrintEntryExitLog;
import validators.GroupCreateRequestValidator;
import validators.GroupUpdateRequestValidator;
import validators.IRequestValidator;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class UpdateGroupController extends BaseController {

    @Override
    protected boolean validate(Request request) throws BaseException {
        IRequestValidator requestValidator = new GroupUpdateRequestValidator();
        return requestValidator.validate(request);
    }

    public CompletionStage<Result> updateGroup(Http.Request req) {
        Request request = createSBRequest(req, ActorOperations.UPDATE_GROUP.getValue());
       try{
           return handleRequest(request);
       }catch (Exception ex) {
           PrintEntryExitLog.printExitLogOnFailure(
                   request,
                   new BaseException(
                           ResponseCode.GS_UDT_08.getErrorCode(),
                           ex.getMessage(),
                           ResponseCode.CLIENT_ERROR.getResponseCode()));
           return CompletableFuture.supplyAsync(() -> StringUtils.EMPTY)
                   .thenApply(result -> ResponseHandler.handleFailureResponse(ex, request));
       }
    }
}
