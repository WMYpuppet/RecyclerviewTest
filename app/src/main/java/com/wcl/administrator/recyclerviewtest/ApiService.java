package com.wcl.administrator.recyclerviewtest;

import com.wcl.administrator.recyclerviewtest.bean.ArticleBean;
import com.wcl.administrator.recyclerviewtest.bean.HomeBanner;
import com.wcl.administrator.recyclerviewtest.bean.LoginBean;
import com.wcl.administrator.recyclerviewtest.bean.NaviBean;
import com.wcl.administrator.recyclerviewtest.bean.ProjectBean;
import com.wcl.administrator.recyclerviewtest.bean.ProjectListBean;
import com.wcl.administrator.recyclerviewtest.http.ResponseBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 作者：Created by Administrator on 2021/4/2.
 * 邮箱：
 */
public interface ApiService {
    /**
     * 1.2 首页banner
     *
     * @return
     */
    @GET("/banner/json")
    Observable<HomeBanner> getBannerList();

    /**
     * 1.1 首页文章列表
     *
     * @return
     */
    @GET("/article/list/{page}/json")
    Observable<ArticleBean> getArticle(@Path("page") int page);


    /**
     * 3.1 导航数据
     *
     * @return
     */
    @GET("/navi/json")
    Observable<NaviBean> getNaviData();

    /**
     * 4.1 项目分类
     *
     * @return
     */
    @GET("/project/tree/json")
    Observable<ProjectBean> getProjectSubject();

    /**
     * 4.2 项目列表数据
     *
     * @return
     */
      @GET("/project/list/{page}/json")
      Observable<ProjectListBean> getProjectList(@Path("page") int page, @Query("cid") int cid);


    /**
     * 6.2 收藏站内文章
     *
     * @param id
     * @return
     */
    @POST("/lg/collect/{id}/json")
    Observable<ResponseBean> insideCollect(@Path("id") int id);

    /**
     * 6.4 取消收藏
     * 6.4.1 文章列表
     * /lg/uncollect_originId/2333/json
     * @return
     */
    @POST("/lg/uncollect_originId/{id}/json")
    Observable<ResponseBean> articleListUncollect(@Path("id") int id);


    /**
     * 5.1 登录
     *
     * @param username
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("/user/login")
    Observable<ResponseBean<LoginBean>> postLogin(@Field("username") String username,
                                                  @Field("password") String password);


    /**
     * 5.2 注册
     *
     * @param username
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("/user/register")
    Observable<ResponseBean> postRegister(@Field("username") String username,
                                          @Field("password") String password,
                                          @Field("repassword") String repassword);
}
