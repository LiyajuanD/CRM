package com.briup.crmcustomer.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;

import java.util.Collections;

/**
 * 使用Mybatis-plus提供的逆向工程，实现代码生成
 * 实现逆向生成对应的代码
 */
public class Generator {
    public static void main(String[] args) {
        //1.连接信息；
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/crm-project?serverTimezone=UTC", "root", "root")
                .globalConfig(builder -> {
                    builder.author("briup") // 设置作者  代码注释
                            .outputDir("E:\\Briup\\sts-4.6.0.RELEASE\\test-sts-workSpace\\CRM\\crm-customer\\src\\main\\java"); // 指定java代码输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.briup") // 设置父包名
                            .moduleName("crmcustomer") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "E:\\Briup\\sts-4.6.0.RELEASE\\test-sts-workSpace\\CRM\\crm-customer\\src\\main\\resources\\Mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("cst_activity") // 设置需要生成的表名
                            .addTablePrefix("cst_","sal","sys"); // 设置过滤表前缀
                })
                .execute();
    }
}
