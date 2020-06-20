package com.itsq.pojo.entity;

import lombok.Data;

@Data
public class SteamOpenIDIdentity {

    public SteamOpenIDIdentity()
    {
        this.ReturnTo = "/";
    }

    // 获取用户图像
    // </summary>
    public String Avatar;

    // 获取用户中心地址
    // </summary>
    public String Profile;

    // 获取Steam ID
    // </summary>
    public String SteamId;

    // 获取用户名称
    // </summary>
    public String UserName;

    // 获取返回地址
    // </summary>
    public String ReturnTo;
}
