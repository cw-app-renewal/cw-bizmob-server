package com.mcnc.smart.hybrid.server.web.control;


import com.mcnc.common.util.JsonUtil;
import com.mcnc.common.util.SmartException;
import com.mcnc.common.util.StringUtil;
import com.mcnc.portal.auth.LicenseReader;
import com.mcnc.smart.common.logging.ILogger;
import com.mcnc.smart.common.logging.LogType;
import com.mcnc.smart.common.logging.LoggerService;
import com.mcnc.smart.hybrid.common.json.SimpleJsonResponse;
import com.mcnc.smart.hybrid.common.server.MessageObject;
import com.mcnc.smart.hybrid.common.server.MessageObject.TYPE;
import com.mcnc.smart.hybrid.server.web.api.MessageAware;
import com.mcnc.smart.hybrid.server.web.api.MessageBinder;
import com.mcnc.smart.hybrid.server.web.api.SessionAware;
import com.mcnc.smart.hybrid.server.web.api.TrcodeValidator;
import com.mcnc.smart.hybrid.server.web.dao.ErrorMessageDao;
import com.mcnc.smart.hybrid.server.web.dao.JournalDao;
import com.mcnc.smart.hybrid.server.web.exception.SessionTimeoutException;
import com.mcnc.smart.hybrid.server.web.model.TransactionInfo;
import com.mcnc.smart.hybrid.server.web.service.JsonSchemaValidator;
import com.mcnc.smart.session.HttpSmartSession;
import com.mcnc.smart.session.ISmartSession;
import com.mcnc.support.license.model.License;
import com.mcnc.support.license.model.License.DEPLOY;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping({ "" })
public class MessageController implements TrcodeValidator {

    private static ILogger logger = LoggerService.getLogger(MessageController.class);

    private static boolean k = false;

    private static boolean l = false;

    private int m = 0;

    private int seed = 0;

    private String host = null;

    static final String a = "요청 처리에 실패했습니다. 잠시 후에 다시 시도해 주세요.";

    static final String b = "ROOT";

    static final String c = "0000";

    static final String d = "0002";

    static final String e = "0001";

    static final String f = "0003";

    static final String g = "0004";

    static final String h = "0019";

    @Autowired
    MessageBinder<JsonNode> jsonSvc;

    @Autowired
    JsonSchemaValidator jsonValidator;

    @Autowired
    ErrorMessageDao errorMessageDao;

    @Autowired
    LicenseReader licenseReader;

    @Autowired
    MessageAware messageAwareProcessor;

    @Autowired
    SessionAware sessionAwareProcessor;

    @Autowired
    JournalDao journalDao;

    License i = null;


    public MessageController() {
        this.initSeed();

        try {
            this.host = InetAddress.getLocalHost().getHostAddress();
        }
        catch (UnknownHostException var5) {
            this.host = StringUtil.defaultIfBlank(System.getProperty("SERVER_IP"), "");
        }
        finally {
            if (StringUtil.isBlank(this.host)) {
                this.host = "X-127.0.0.1";
            }

        }
    }


    @RequestMapping(value = { "/{TRCode}" }, method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView handleMessage(HttpServletRequest request, HttpServletResponse response,
            @PathVariable("TRCode") String pathTrCode, @RequestParam("message") String message) {

        String trCode = this.escape(pathTrCode);
        if (!this.validate(pathTrCode)) {
            logger.warn("Invalid trcode format!");
            throw new SmartException("Invalid trcode format!");
        }
        else {
            logger.info(String.format("===== Start %s  >>>>> ", trCode));
            ModelAndView mv = null;

            try {
                if (!this.validate()) {
                    mv = new ModelAndView("jsonView");
                    mv.addObject("header", buildErrorResponse(trCode, trCode + "ROOT" + "0000", e(), e(), (Map) null)
                            .findPath("header"));
                }
                else {
                    mv = this._processMainLogic(request, response, trCode, message);
                }
            }
            catch (Throwable var12) {
                logger.error("Unknown Exception!", var12);
                mv = new ModelAndView("jsonView");
                JsonNode var8 = buildErrorResponse(trCode, trCode + "ROOT" + "0001",
                        this.errorMessageDao.getServerMessage(trCode + "ROOT" + "0001"), (String) null, (Map) null);
                mv.addObject("header", JsonUtil.find(var8, "header"));
            }
            finally {
                logger.info(String.format("<<<<<  %s End =====", trCode));
            }

            return mv;
        }
    }


    @RequestMapping(value = { "/logout" }, method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseStatus(HttpStatus.OK)
    public void logout(HttpServletRequest var1, @RequestParam(value = "user_id", required = false) String var2)
            throws SessionTimeoutException {
        ISmartSession var3 = getSession(var1.getSession(false));
        String var4 = this.a(var1);
        long var5 = this.b();
        this.c();
        if (var3 != null) {
            TransactionInfo var7 = new TransactionInfo(var5, var3.getId(), "LOGOUT", this.host, var4);
            String var8 = (String) var3.getAttribute("user_id");
            String var9 = (String) var3.getAttribute("device_id");
            if (!StringUtil.equals(var2, var8)) {
                this.journalDao.writeSimple(var5, "LOGOUT", this.host, var4, var2, false, "HTTP_401",
                        "user_id mismatch!");
                throw new SessionTimeoutException("user_id mismatch!");
            }

            this.clearSession(var7, var3, var2, var9);
            this.journalDao.writeSimple(var5, "LOGOUT", this.host, var4, var2, true, (String) null, (String) null);
        }

        logger.info(String.format("%s logout!", var2));
    }


    @RequestMapping(value = { "/LOGOUT" }, method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseStatus(HttpStatus.OK)
    public void logOut(HttpServletRequest var1, @RequestParam(value = "user_id", required = false) String var2)
            throws SessionTimeoutException {
        this.logout(var1, var2);
    }


    public void clearSession(TransactionInfo var1, ISmartSession var2, String var3, String var4) {
        this.sessionAwareProcessor.beforeClear(var1.getTransactionId(), var3, var4, var2, var1.getServerIp(),
                var1.getClientIp());
        invalidateSession(var2);
        this.sessionAwareProcessor.afterClear((long) this.m, var3, var4, var1.getSessionId(), var1.getServerIp(),
                var1.getClientIp());
    }


    @RequestMapping(value = { "/validate/{TRCode}" }, method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView handleMessageWithValidation(HttpServletRequest var1, HttpServletResponse var2,
            @PathVariable("TRCode") String var3, @RequestParam("message") String var4) {
        String var5 = this.escape(var3);
        if (!this.validate(var3)) {
            logger.warn("Invalid trcode format!");
            throw new SmartException("Invalid trcode format!");
        }
        else {
            return this.validate() ? this.b(var1, var2, var5, var4) : null;
        }
    }


    public static void invalidateSession(ISmartSession var0) {
        logger.trace("Start::invalidateSession()");
        logger.trace("  > session : " + var0);

        try {
            Enumeration var1 = var0.getAttributeNames();

            while (var1.hasMoreElements()) {
                var0.removeAttribute((String) var1.nextElement());
            }
        }
        catch (Exception var10) {
            ;
        }
        finally {
            try {
                var0.invalidate();
            }
            catch (Exception var9) {
                ;
            }

        }

        logger.trace("End::invalidateSession()");
    }


    public static JsonNode buildErrorResponse(String var0, String var1, String var2, String var3,
            Map<String, String> var4) {
        if (isTraceable()) {
            logger.trace("Start::buildErrorResponse()");
            logger.trace("  > trcode : " + var0);
            logger.trace("  > errorCode : " + var1);
            logger.trace("  > errorText : " + var2);
            logger.trace("  > infoText : " + var3);
            logger.trace("  > otherValues : " + var4);
        }

        ObjectNode var5 = JsonUtil.objectNode();
        ObjectNode var6 = var5.putObject("header");
        var6.put("trcode", var0);
        var6.put("result", false);
        var6.put("error_code", var1);
        var6.put("error_text", var2);
        var6.put("info_text", var3);
        if (var4 != null && var4.size() > 0) {
            Iterator var7 = var4.entrySet().iterator();

            while (var7.hasNext()) {
                Entry var8 = (Entry) var7.next();
                var6.put((String) var8.getKey(), (String) var8.getValue());
            }
        }

        if (isTraceable()) {
            logger.trace("  > RV(objNode) : " + var5);
            logger.trace("End::buildErrorResponse()");
        }

        return var5;
    }


    public static JsonNode buildErrorResponse(long var0, String var2, String var3, String var4, String var5,
            Map<String, String> var6) {
        if (isTraceable()) {
            logger.trace(var0, "Start::buildErrorResponse()");
            logger.trace(var0, "  > trcode : " + var2);
            logger.trace(var0, "  > errorCode : " + var3);
            logger.trace(var0, "  > errorText : " + var4);
            logger.trace(var0, "  > infoText : " + var5);
            logger.trace(var0, "  > otherValues : " + var6);
        }

        ObjectNode var7 = JsonUtil.objectNode();
        ObjectNode var8 = var7.putObject("header");
        var8.put("trcode", var2);
        var8.put("result", false);
        var8.put("error_code", var3);
        var8.put("error_text", var4);
        var8.put("info_text", var5);
        if (var6 != null && var6.size() > 0) {
            Iterator var9 = var6.entrySet().iterator();

            while (var9.hasNext()) {
                Entry var10 = (Entry) var9.next();
                var8.put((String) var10.getKey(), (String) var10.getValue());
            }
        }

        if (isTraceable()) {
            logger.trace(var0, "  > RV(objNode) : " + var7);
            logger.trace(var0, "End::buildErrorResponse()");
        }

        return var7;
    }


    public static List<String> getLocalIPs() {
        ArrayList var0 = new ArrayList();

        try {
            var0.add("127.0.0.1");
            InetAddress[] var1 = InetAddress.getAllByName(InetAddress.getLocalHost().getHostName());

            for (int var2 = 0; var2 < var1.length; ++var2) {
                if (var1[var2].getHostAddress().matches("\\b\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\b")) {
                    var0.add(var1[var2].getHostAddress());
                }
            }
        }
        catch (UnknownHostException var3) {
            logger.warn("UnknownHostException", var3);
            var0.add("127.0.0.1");
        }

        return var0;
    }


    public static ISmartSession getSession(HttpSession var0) {
        return var0 == null ? null : new HttpSmartSession(var0);
    }


    protected JsonNode buildResponseJsonHeader(long var1, MessageObject var3, ISmartSession var4) {
        if (isTraceable()) {
            logger.trace(var1, "Start::buildResponseJsonHeader()");
            logger.trace(var1, "  > obj : " + var3);
        }

        Object var5 = null;
        JsonNode var6 = null;
        var6 = (JsonNode) var3.get(TYPE.JSON);
        var5 = JsonUtil.find(var6, "header");
        if (((JsonNode) var5).isMissingNode()) {
            logger.warn(var1, "The header node is missing.");
            var5 = ((ObjectNode) var6).putObject("header");
        }

        String var8;
        if (var3.containsKey(TYPE.ERROR)) {
            SimpleJsonResponse var7 = (SimpleJsonResponse) var3.get(TYPE.ERROR);
            ((ObjectNode) var5).put("result", false);
            ((ObjectNode) var5).put("error_code", var7.getResultCode());
            var8 = this.errorMessageDao.getMessage(var7.getResultCode());
            logger.debug("TYPE ERROR: " + var8);
            if (var8 == null || var8.length() == 0) {
                logger.debug("ERROR MESSAGE: " + var8);
                logger.debug("RESULT_TEXT MESSAGE: " + var7.getResultText());
                if (var7.getResultText() != null && var7.getResultText().length() != 0) {
                    var8 = var7.getResultText();
                }
                else {
                    var8 = "요청 처리에 실패했습니다. 잠시 후에 다시 시도해 주세요.";
                }

                logger.debug("FINAL ERROR MESSAGE: " + var8);
            }

            ((ObjectNode) var5).put("error_text", var8);
            ((ObjectNode) var5).put("info_text", var7.getInfoText());
        }
        else {
            ((ObjectNode) var5).put("result", true);
            ((ObjectNode) var5).put("error_code", "");
            ((ObjectNode) var5).put("error_text", "");
        }

        if (var3.containsKey(TYPE.SESSION) && JsonUtil.find(var6, "/header/result").getBooleanValue()) {
            HashMap var11 = (HashMap) var3.get(TYPE.SESSION);
            var8 = (String) var11.get("id");
            if (var8 != null && !var8.equals("Unknown")) {
                Iterator var9 = var11.entrySet().iterator();

                while (var9.hasNext()) {
                    Entry var10 = (Entry) var9.next();
                    var4.setAttribute((String) var10.getKey(), var10.getValue());
                    logger.trace(var1, "Add to the session : " + var10);
                }
            }
            else {
                logger.trace("session is unknown.");
            }
        }

        if (isTraceable()) {
            logger.trace(var1, "  > RV(rootNode) : " + var6);
            logger.trace(var1, "End::buildResponseJsonHeader()");
        }

        return var6;
    }


    protected static boolean isTraceable() {
        return logger.getLoggingLevel().getLevel() >= LogType.TRACE.getLevel();
    }


    // trCodeEsc, message
    private ModelAndView _processMainLogic(HttpServletRequest request, HttpServletResponse response, String trCode,
            String message) {
        ModelAndView mv = new ModelAndView("jsonView");
        JsonNode var6 = (JsonNode) this.jsonSvc.bind(message);
        JsonNode var8 = null;
        JsonNode var9 = null;
        ISmartSession var10 = getSession(request.getSession(false));
        TransactionInfo var11 = null;
        ISmartSession var12 = null;
        long var13 = 0L;
        String var15 = var10 == null ? "Unknown" : var10.getId();
        String var16 = this.a(request);
        String var17 = null;
        String var18 = null;
        if (var6 != null && !var6.isMissingNode()) {
            JsonNode var7 = JsonUtil.find(var6, "/header/transaction_id");
            if (var7.isMissingNode()) {
                var13 = this.b();
                this.c();
            }
            else {
                var13 = var7.getLongValue();
            }

            if (StringUtil.equalsIgnoreCase("LOGIN", trCode)) {
                var17 = JsonUtil.find(var6, "/body/user_id").getValueAsText();
                var18 = JsonUtil.find(var6, "/body/device_id").getValueAsText();
                this.sessionAwareProcessor.beforeCreation(var13, var17, var18, var10, this.host, var16);
                var12 = getSession(request.getSession(true));
                var12.setAttribute("user_id", var17);
                var12.setAttribute("device_id", var18);
                var15 = var12.getId();
                this.journalDao.writeClientRequest(var13, trCode, this.host, var16, var12);
                logger.trace(var13, "Creates new session : " + var15);
                var11 = new TransactionInfo(var13, var15, trCode, this.host, var16);
            }
            else {
                try {
                    var11 = new TransactionInfo(var13, var15, trCode, this.host, var16);
                    this.journalDao.writeClientRequest(var13, trCode, this.host, var16, var10);
                    var12 = this.jsonSvc.checkAndGetSessionInfo(var11, request);
                }
                catch (SessionTimeoutException var22) {
                    logger.warn(var13, "Session Timeout!");
                    JsonNode var20 = buildErrorResponse(var13, trCode, "ERR000",
                            this.errorMessageDao.getMessage("ERR000"), (String) null, (Map) null);
                    mv.addObject("header", JsonUtil.find(var20, "header"));
                    return mv;
                }
            }

            this.messageAwareProcessor.before(var11, message, var6, var12);

            try {
                String var23 = this.jsonSvc.checkMessage(var11, var6);
                if (StringUtil.isBlank(var23)) {
                    if (StringUtil.equalsIgnoreCase("LOGIN", trCode)) {
                        var8 = this.a(var11, var6, var12);
                        if (JsonUtil.find(var8, "/header/result").getBooleanValue()) {
                            this.sessionAwareProcessor.afterCreation(var13, var17, var18, var12, this.host, var16);
                        }
                        else {
                            invalidateSession(var12);
                        }
                    }
                    else {
                        MessageObject var24 = this.jsonSvc.dispacth(var11, var6, var12);
                        var8 = this.buildResponseJsonHeader(var13, var24, var12);
                    }
                }
                else {
                    var8 = buildErrorResponse(var13, trCode, var23, this.errorMessageDao.getMessage(var23),
                            (String) null, (Map) null);
                }
            }
            catch (Exception var21) {
                logger.debug(var13, "Exception!", var21);
                var8 = buildErrorResponse(var13, trCode, trCode + "ROOT" + "0001", var21.getLocalizedMessage(),
                        (String) null, (Map) null);
            }

            mv.addObject("header", JsonUtil.find(var8, "header"));
            var9 = JsonUtil.find(var8, "body");
            if (!var9.isMissingNode()) {
                mv.addObject("body", var9);
            }

            this.journalDao.writeClientResponse(var13, trCode, this.host, var16, var12, var8);
            this.messageAwareProcessor.after(var11, var6, var8, var12);
            return mv;
        }
        else {
            logger.error("Failed to parsing Json!");
            logger.debug("Failed to parsing Json! Invalid message : " + message);
            JsonNode var19 = buildErrorResponse(var13, trCode, trCode + "ROOT" + "0019",
                    this.errorMessageDao.getServerMessage(trCode + "ROOT" + "0019"), (String) null, (Map) null);
            mv.addObject("header", JsonUtil.find(var19, "header"));
            return mv;
        }
    }


    private JsonNode a(TransactionInfo var1, JsonNode var2, ISmartSession var3) {
        long var4 = var1.getTransactionId();
        MessageObject var6 = null;
        MessageObject var7 = null;
        Object var8 = null;
        String var9 = JsonUtil.find(var2, "/body/app_key").getValueAsText();
        if (!this.i.containsApp(var9)) {
            logger.fatal(var4, e());
            logger.fatal(var4, "YOUR APPLICATION '" + var9 + "' IS NOT ALLOWED.");
            return buildErrorResponse(var4, "LOGIN", "LOGINROOT0002", e(), (String) null, (Map) null);
        }
        else {
            var6 = this.jsonSvc.dispacth(var1, var2, var3);
            var8 = this.buildResponseJsonHeader(var4, var6, var3);
            if (JsonUtil.find((JsonNode) var8, "/header/result").getBooleanValue()) {
                String var10 = JsonUtil.find(var2, "/body/legacy_trcode").getValueAsText();
                JsonNode var11 = JsonUtil.find(var2, "/body/legacy_message");
                if (!StringUtil.isBlank(var10) && !var11.isMissingNode()) {
                    TransactionInfo var12 = new TransactionInfo(var1.getTransactionId(), var1.getSessionId(), var10,
                            var1.getServerIp(), var1.getClientIp());
                    logger.info(var4, "++++ Start the legacy : " + var10);
                    this.journalDao.writeClientRequest(var4, var10, var12.getServerIp(), var12.getClientIp(), var3);
                    String var13 = this.jsonSvc.checkMessage(var12, var11);
                    if (StringUtil.isBlank(var13)) {
                        var7 = this.jsonSvc.dispacth(var12, var11, var3);
                        JsonNode var14 = this.buildResponseJsonHeader(var4, var7, var3);
                        this.journalDao.writeClientResponse(var4, var10, var12.getServerIp(), var12.getClientIp(), var3,
                                var14);
                        if (JsonUtil.find(var14, "/header/result").getBooleanValue()) {
                            ObjectNode var18 = JsonUtil.objectNode();
                            ObjectNode var19 = var18.putObject("header");
                            ObjectNode var17 = var18.putObject("body");
                            var19.put("result", true);
                            var19.put("error_code", "");
                            var19.put("error_text", "");
                            var19.put("info_text", (String) JsonUtil.getValue(var14, "info_text"));
                            var19.put("message_version", (String) JsonUtil.getValue(var14, "message_version"));
                            var19.put("login_session_id", var1.getSessionId());
                            var19.put("trcode", var1.getTrcode());
                            var17.put("user_type", JsonUtil.find((JsonNode) var8, "/body/user_type"));
                            var17.put("company_id", JsonUtil.find((JsonNode) var8, "/body/company_id"));
                            var17.put("remote_wipe", JsonUtil.find((JsonNode) var8, "/body/remote_wipe"));
                            var17.put("access_limit", JsonUtil.find((JsonNode) var8, "/body/access_limit"));
                            var17.put("app_tester", JsonUtil.find((JsonNode) var8, "/body/app_tester"));
                            var17.put("attachment_download",
                                    JsonUtil.find((JsonNode) var8, "/body/attachment_download"));
                            var17.put("license_no", this.i.getLicenseNo());
                            var17.put("legacy_message", var14);
                            var17.put("legacy_trcode", var10);
                            logger.trace(var4, "Success of the legacy! ");
                            var8 = var18;
                        }
                        else {
                            var13 = JsonUtil.find(var14, "/header/error_code").getValueAsText();
                            logger.trace(var4, "Failure of the legacy! " + var13);
                            String var15 = this.errorMessageDao.getMessage(var13);
                            if (var15 == null || var15.length() == 0) {
                                String var16 = JsonUtil.find(var14, "/header/error_text").getValueAsText();
                                if (var16 != null && var16.length() > 0) {
                                    var15 = var16;
                                }
                                else {
                                    var15 = "요청 처리에 실패했습니다. 잠시 후에 다시 시도해 주세요.";
                                }
                            }

                            var8 = buildErrorResponse(var1.getTransactionId(), var1.getTrcode(), var13, var15,
                                    (String) null, (Map) null);
                        }
                    }
                    else {
                        logger.trace(var4, "Failure of the legacy! " + var13);
                        var8 = buildErrorResponse(var1.getTransactionId(), var1.getTrcode(), var13,
                                this.errorMessageDao.getMessage(var13), (String) null, (Map) null);
                    }

                    logger.info(var4, "++++ End the legacy : " + var10);
                }
            }

            return (JsonNode) var8;
        }
    }


    private ModelAndView b(HttpServletRequest var1, HttpServletResponse var2, String var3, String var4) {
        JsonNode var5 = null;
        ObjectNode var6 = null;
        ModelAndView var7 = null;
        logger.info(String.format("===== Start %s with VALIDATION >>>>> ", var3));

        try {
            var5 = (JsonNode) this.jsonSvc.bind(var4);
            JsonNode var8;
            if (!this.jsonValidator.validateRequest(var3, var5)) {
                logger.warn("Failed to validate request JSON!");
                var7 = new ModelAndView("jsonView");
                var8 = buildErrorResponse(var3, var3 + "ROOT" + "0003",
                        this.errorMessageDao.getServerMessage(var3 + "ROOT" + "0003"), (String) null, (Map) null);
                var7.addObject("header", JsonUtil.find(var8, "header"));
            }
            else {
                var7 = this._processMainLogic(var1, var2, var3, var4);
                var6 = JsonUtil.objectNode();
                JsonUtil.putValue(var6, "header", (JsonNode) var7.getModel().get("header"));
                JsonUtil.putValue(var6, "body", (JsonNode) var7.getModel().get("body"));
                if (JsonUtil.find(var6, "/header/result").getBooleanValue()
                        && !this.jsonValidator.validateResponse(var3, var6)) {
                    logger.warn("Failed to validate response JSON!");
                    var7 = new ModelAndView("jsonView");
                    var8 = buildErrorResponse(var3, var3 + "ROOT" + "0004",
                            this.errorMessageDao.getServerMessage(var3 + "ROOT" + "0004"), (String) null, (Map) null);
                    var7.addObject("header", JsonUtil.find(var8, "header"));
                }
            }
        }
        catch (Exception var10) {
            logger.error("Validation Exception!", var10);
            var7 = new ModelAndView("jsonView");
            JsonNode var9 = buildErrorResponse(var3, var3 + "ROOT" + "0001", var10.getMessage(), (String) null,
                    (Map) null);
            var7.addObject("header", JsonUtil.find(var9, "header"));
        }

        logger.info(String.format("<<<<<  %s End with VALIDATION =====", var3));
        return var7;
    }


    private String a(HttpServletRequest var1) {
        String[] var2 = new String[] { "Proxy-Client-IP", "proxy-client-ip", "PROXY-CLIENT-IP", "X-Forwarded-For",
                "x-forwarded-for", "X-FORWARDED-FOR", "HTTP_X_Forwarded-For", "http_x_forwarded-for",
                "HTTP_X_FORWARDED_FOR", "WL-Proxy-Client-IP", "wl-proxy-client-ip", "WL-PROXY-CLIENT-IP" };
        String var3 = null;
        String[] var4 = var2;
        int var5 = var2.length;

        for (int var6 = 0; var6 < var5; ++var6) {
            String var7 = var4[var6];
            var3 = var1.getHeader(var7);
            if (var3 != null && var3.length() > 0) {
                break;
            }
        }

        if (var3 == null || var3.length() == 0) {
            var3 = var1.getRemoteAddr();
        }

        return var3;
    }


    private synchronized boolean validate() {
        if (l && k) {
            return true;
        }
        else {
            if (!l) {
                if (this.licenseReader == null) {
                    logger.fatal("CANNOT READ LICENSE! THE SERVER STOPS!");
                }
                else {
                    try {
                        this.i = this.licenseReader.getLicense();
                    }
                    catch (Exception var4) {
                        logger.warn("Failed to read license!", var4);
                        this.i = null;
                    }

                    if (this.i == null) {
                        logger.fatal("FAILED TO READ LICENSE! THE SERVER STOPS!");
                    }
                    else {
                        l = true;
                        if (this.i.expired()) {
                            logger.fatal("LICENSE IS EXPIRED! THE SERVER STOPS!");
                        }
                        else if (this.i.getDeployType() == DEPLOY.TEST) {
                            logger.info("TEST LICENSE!");
                            k = true;
                        }
                        else {
                            List var1 = getLocalIPs();
                            Iterator var2 = var1.iterator();

                            while (var2.hasNext()) {
                                String var3 = (String) var2.next();
                                if (this.i.containsIp(var3)) {
                                    k = true;
                                    logger.info("Checked! Your IP " + var3 + " is allowed.");
                                    break;
                                }

                                logger.warn("YOUR IP " + var3 + " IS NOT ALLOWED.");
                            }
                        }
                    }
                }
            }
            else if (!k) {
                logger.fatal(e());
                logger.error(e());
                logger.warn(e());
                logger.info(e());
                logger.debug(e());
                logger.trace(e());
                System.out.println(e());
            }

            return l && k;
        }
    }


    private synchronized long b() {
        return (long) this.seed * 10000000000L + (long) this.m;
    }


    private synchronized void c() {
        if (this.m == Integer.MAX_VALUE) {
            this.m = 0;
            this.initSeed();
        }
        else {
            ++this.m;
        }

    }


    private synchronized void initSeed() {
        Calendar cal = Calendar.getInstance();
        this.seed = (cal.get(6) << 12) + (cal.get(11) << 6) + cal.get(12);
        this.seed += cal.get(1) % 100 << 21;
        this.seed = (this.seed << 4) + (int) (System.currentTimeMillis() & 15L);
        logger.info("New Seed : " + this.seed);
    }


    private static String e() {
        return "[FATAL] LICENSE VIOLATION! YOU ARE NOT ALLOWED TO USE THE BIZMOB SERVER.";
    }


    @ExceptionHandler({ SmartException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    void a(SmartException var1, HttpServletResponse var2) {
        logger.error("Failed! Code: " + HttpStatus.BAD_REQUEST + ", Cause: " + var1.getMessage());
        var2.setHeader("error_code", HttpStatus.BAD_REQUEST.toString());
    }


    @ExceptionHandler({ SessionTimeoutException.class })
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    void a(SessionTimeoutException var1, HttpServletResponse var2) {
        logger.error("Failed! Code: " + HttpStatus.UNAUTHORIZED + ", Cause: " + var1.getMessage());
        var2.setHeader("error_code", HttpStatus.UNAUTHORIZED.toString());
    }


    public String escape(String var1) {
        return var1;
    }


    public boolean validate(String trCode) {
        boolean ret = true;
        if (StringUtil.equalsIgnoreCase(trCode, "LOGIN")) {
            return true;
        }
        else {
            if (StringUtil.isBlank(trCode)) {
                ret = false;
            }
            else if (trCode.length() < 6) {
                ret = false;
            }
            else if (trCode.length() > 7) {
                ret = false;
            }
            else if (!StringUtil.isAlphanumeric(trCode)) {
                ret = false;
            }

            return ret;
        }
    }
}