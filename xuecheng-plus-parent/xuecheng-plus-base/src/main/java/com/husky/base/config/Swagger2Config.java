package com.husky.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by IntelliJ IDEA.
 * User: 周圣杰
 * Date: 2023/4/6
 * Time: 19:07
 * Description: 描述该类的功能
 */
@EnableSwagger2
@Configuration
public class Swagger2Config {
    @Bean
    public Docket createRestApi() {
        String groupName="3.X版本";
        Docket docket=new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                //分组名称
                .groupName(groupName)
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.husky"))
                .paths(PathSelectors.any())
                .build();
                // 配置swagger带上token
//                .globalRequestParameters(Collections.singletonList(
//                        new RequestParameterBuilder()
//                                .name("Access-Token")
//                                .description("Access-Token")
//                                .in(ParameterType.HEADER)
//                                .required(false)
//                                .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
//                                .build()));
        return docket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("学成在线项目")
                .version("1.0")
                .description("学成在线项目接口文档")
                .contact(new Contact("husky","https://github.com/Husky-Jie","1583893320@qq.com"))
                .build();
    }
}
