package com.briup.crmgateway.config;

import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 *   gateway中不能整合springMVC
 *   使用配置类实现将所有的路由服务整合到当前gateway中的swagger界面中
 *
 */
@Primary
@Component
public class SwaggerProvider implements SwaggerResourcesProvider {
    private static final String API_URI = "/v2/api-docs";
    private RouteLocator routeLocator;
    private GatewayProperties gatewayProperties;

    public SwaggerProvider(RouteLocator routeLocator,GatewayProperties gatewayProperties){
        this.routeLocator = routeLocator;
        this.gatewayProperties = gatewayProperties;
    }
    @Override
    public List<SwaggerResource> get() {
        List<String> routes = new ArrayList<>();
        routeLocator.getRoutes().subscribe(route->routes.add(route.getId()));
        List<SwaggerResource> list = new ArrayList<>();
        gatewayProperties.getRoutes().stream()
                .filter(routeDefinition->routes.contains(routeDefinition.getId()))
                .forEach(routeDefinition ->
                        routeDefinition.getPredicates().stream()
                                .filter(predicateDefinition ->
                                        "Path".equalsIgnoreCase(predicateDefinition.getName()))
                                .forEach(predicateDefinition -> {
                                    System.out.println(predicateDefinition);
                                    list.add(swaggerResource(routeDefinition.getId(),
                                            predicateDefinition.getArgs().get(NameUtils.GENERATED_NAME_PREFIX+"0")
                                                    .replace("/**",API_URI)));
                                })
                );
        return list;
    }


    private SwaggerResource swaggerResource(String name,String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }
}