---
title:  趣评(QuPing)类虎扑评分项目
tags:
- 自己写的项目
- 后端
categories:
- 自己写的项目
---



# 趣评(QuPing)

> 这是一个仿虎扑的评分模块项目
>
> 技术栈为：Spring、SpringBoot、Mybatis、Redis、Lombok、Hutool
>
> 用户可以注册登录，并且对某些项目进行评分。

## 接口功能列表

### 用户相关接口

+ 根据邮箱获取验证码
+ 根据手机号获取验证码
+ 验证码登录
+ 密码登录
+ 登出
+ 查看个人资料

### 评分相关接口

+ 用户创建新评分
+ 用户进行评分
+ 获取用户对应评分
+ 获取评分

## 开发记录

+ 2024/11/21
  + 重启开发计划
  + 更新README
  + 更新sql文件
+ 2024/11/27
  + 登录页面样式创建

## 开发计划

+ 用户评论
  + 创建评论实体类
  + 创建评论表结构
  + 评论增删查接口
+ 前端
  + 新建Vue前端项目
  + 登录界面

## 接口流程

### 用户通过验证码登录/注册

> 前端传入用户手机号->生成验证码保存在Redis中key:手机号,value:验证码->通过第三方API将验证码发送至用户手机->用户输入验证码->后端根据手机号在Redis中获取验证码并比对->通过手机号查找用户是否注册过，没有则创建一个账号->生成Token保存用户session并返回给前端
>
> 为了防止验证码的滥用，每次在要发送验证码前，会去redis中判断一下，该手机号是否正在进行登录流程，也就是redis中是否已经有这个手机号关联的验证码了，如果有则拒绝生成新的。

### 生成Token返回

> 首先用户登录后，会获取到后端返回的token，前端凭借每次请求携带用户token来代表用户身份。
>
> 在后端配置了一个SpringMVC的拦截器，实现了前置拦截方法，在这个方法中每次需要身份的请求都会被拦截并且获取请求体中的token，并将token解析成用户的上下文信息，并保存在用户的全局上下文工具类中，方便后续使用。
>
> token本质上是一个随机的字符串，为了防止重复，使用了uuid，并且token关联用户的session是靠构造以token为key用户session为value的键值对。所以解析token就是根据token去redis中获取用户的session字符串，然后反序列化成User实体。
>
> 在注册/登录的时候，将用户的实体信息根据序列化成字符串，并且生成一个随机token与其关联，保存在redis中并返回。
>
> 但是这里有一个问题，如果用户不停的发送登入请求，那么后端就会不停的生成token，这样会导致资源浪费。所以需要某种手段来判断用户是否已经登录。

### 用户登入状态的判断

> 在用户登录后，需要生成token并返回，如果此时该用户已经登录了，则返回已经生成的token，避免单个用户重复生成不同token
>
> 通过redis中构造key为userID,value为token的键值对来保存用户的登录状态。
>
> 在登录后，生成token前，根据用户id去redis中查询一下是否已经有对应用户的token了，如果有则判断该token是否有效，如果有效则直接返回，否则生成新的token并保存在redis后再返回。

### 分页查询设计

> 用于带条件的查询，并且返回分页后的结果

前端传入的数据(现阶段条件只做字符串匹配)

```json
{
    "pageNumber": 1,
    "pageSiez": 20,
    "condition": "abc"
}
```

后端数据类：

```java
public class PageInfo{
  /**
   * 页码
   */
  private Integer pageNumber = 1;
  /**
   * 每页的数据条数
   */
  private Integer pageSize = 10;
  /**
   * 查询条件，目前只做字符串匹配
   */
  private String condition;

  public void setPageNumber(Integer pageNumber) {
    this.pageNumber = pageNumber >= 1 ? pageNumber : 1;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize >= 1 ? pageSize : 10;
  }
}
```
```java
public class PageResult<T>{
    private Long total = 0L;
    private List<T> dataList;
}
```
