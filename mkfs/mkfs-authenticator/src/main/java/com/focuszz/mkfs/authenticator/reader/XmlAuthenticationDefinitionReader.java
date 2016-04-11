package com.focuszz.mkfs.authenticator.reader;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import com.focuszz.mkfs.authenticator.definition.AuthenticationDefinition;
import com.focuszz.mkfs.authenticator.registry.AuthenticationDefinitionRegistry;
import com.focuszz.mkfs.authenticator.registry.BeanDefinitionRegistry;

public class XmlAuthenticationDefinitionReader implements AuthenticationDefinitionReader {

    private Logger                 logger           = LoggerFactory
        .getLogger(XmlAuthenticationDefinitionReader.class);

    private static final String    LOGIN_ATTR       = "needLogin";
    private static final String    ROLES_ATTR       = "roles";
    private static final String    AUTH_SET_ELEMENT = "authorizationset";
    private static final String    AUTH_ELEMENT     = "authorization";
    private static final String    URI_PREFIX_ATTR  = "uriPrefix";
    private static final String    URI_ATTR         = "uri";
    private static final String    REGEX_ATTR       = "regex";
    public static final String     ROLE_ALL         = "all";

    public static final String     DELIMITER        = ",";

    private BeanDefinitionRegistry registry;

    public XmlAuthenticationDefinitionReader() {
        this(new AuthenticationDefinitionRegistry());
    }

    public XmlAuthenticationDefinitionReader(BeanDefinitionRegistry registry) {
        if (null == registry) {
            throw new NullPointerException("registry can not be null");
        }
        this.registry = registry;
    }

    public void readBeanDefinitions(Resource resource) {
        SAXReader reader = new SAXReader();
        Document doc = null;
        try {
            doc = reader.read(resource.getInputStream());
            final Element root = doc.getRootElement();
            final boolean defaultLogin = strToBoolean(
                StringUtils.trimToEmpty(root.attributeValue(LOGIN_ATTR)));
            final Set<String> defaluRoles = strToSet(root.attributeValue(ROLES_ATTR), ",");
            final AuthenticationDefinition defAuthDefinition = new AuthenticationDefinition(
                defaultLogin, defaluRoles);
            @SuppressWarnings("unchecked")
            List<Element> authSetElements = root.elements(AUTH_SET_ELEMENT);
            for (Element elementSet : authSetElements) {
                parseAuthenticationSet(elementSet, defAuthDefinition);
            }
        } catch (Exception e) {
            logger.error("XmlAuthenticationDefinitionReader::registerBeanDefinitions()", e);
            throw new RuntimeException("权限配置文件读取失败。", e);
        }
    }

    private void parseAuthenticationSet(Element authSet,
                                        AuthenticationDefinition defAuthDefinition) {
        @SuppressWarnings("unchecked")
        List<Element> authElements = authSet.elements(AUTH_ELEMENT);
        if (CollectionUtils.isEmpty(authElements))
            return;
        String uriPrefix = StringUtils.defaultIfBlank(authSet.attributeValue(URI_PREFIX_ATTR), "");
        AuthenticationDefinition authSetDefinition = createAuthenticationDefinition(authSet, "",
            defAuthDefinition);
        for (Element element : authElements) {
            parseAuthenticationElement(element, uriPrefix, authSetDefinition);
        }
    }

    private void parseAuthenticationElement(Element authElement, String uriPrefix,
                                            AuthenticationDefinition parentDefinition) {
        String uri = authElement.attributeValue(URI_ATTR);
        if (StringUtils.isBlank(uri))
            return;
        uri = uriPrefix + "";
        AuthenticationDefinition authDefinition = createAuthenticationDefinition(authElement,
            uriPrefix, parentDefinition);

        if (validateAuthenticationDefinition(authDefinition)) {
            if (authDefinition.isPatternUri()) {
                registry.registerBean(authDefinition.getRegexUri(), authDefinition, true);
            } else {
                registry.registerBean(authDefinition.getUri(), authDefinition);
            }
        }
    }

    private boolean validateAuthenticationDefinition(AuthenticationDefinition authDefinition) {
        return StringUtils.isNotBlank(authDefinition.getUri())
               || StringUtils.isNotBlank(authDefinition.getRegexUri());
    }

    private AuthenticationDefinition createAuthenticationDefinition(Element element,
                                                                    String uriPrefix,
                                                                    AuthenticationDefinition parentDefinition) {
        String uri = element.attributeValue(URI_ATTR);
        String loginStr = element.attributeValue(LOGIN_ATTR);

        boolean login;
        if (StringUtils.isBlank(loginStr)) {
            login = parentDefinition.isLogin();
        } else {
            login = strToBoolean(loginStr);
        }

        String rolesStr = element.attributeValue(ROLES_ATTR);
        Set<String> roles;
        if (StringUtils.isBlank(rolesStr)) {
            roles = parentDefinition.getRoles();
        } else {
            roles = strToSet(rolesStr, DELIMITER);
        }

        if (StringUtils.isNotBlank(uri)) {
            return new AuthenticationDefinition(login, uriPrefix + uri, roles);
        } else {
            String regexUri = element.attributeValue(REGEX_ATTR);
            if (StringUtils.isBlank(regexUri)) {
                regexUri = parentDefinition.getRegexUri();
            }
            return new AuthenticationDefinition(login, regexUri, roles, true);
        }

    }

    private Set<String> strToSet(String str, String delimiter) {
        if (StringUtils.isBlank(str))
            return null;
        Set<String> set = new HashSet<String>();
        String[] arr = StringUtils.trim(str).split(delimiter);
        for (String tmpStr : arr) {
            set.add(tmpStr);
        }
        return set;
    }

    private boolean strToBoolean(String str) {
        str = StringUtils.trimToNull(str);
        return StringUtils.equalsIgnoreCase(str, "true");
    }

    public BeanDefinitionRegistry getRegistry() {
        return registry;
    }

    public void setRegistry(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

}
