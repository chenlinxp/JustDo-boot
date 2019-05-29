package com.justdo.system.article.domain;

import com.justdo.common.domain.BaseBean;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;



/**
 * 文章内容
 * 
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019-05-28 23:27:31
 */
public class ArticleDO extends BaseBean {


    //主键ID
    private String articleId;

	//创建人id
	private String createEmployeeId;
	//最近修改人id
	private String modifyEmployeeId;
	// 点击数量
	private Integer articleHits;
	//评论数量
	private Integer commentsNum;
	//是否删除
	private Integer delFlag;
	//状态
	private Integer articleStatus;
	//创建时间
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	//修改时间
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date modifyTime;
	//标题
	private String articleTitle;
	//内容
	private String articleContent;
	//类型
	private String articleType;
	//标签
	private String articleTags;
	//分类
	private String articleCategories;
	//开启评论
	private Integer allowComment;
	//允许反馈
	private Integer allowFeed;
	//作者
	private String articleAuthor;

    /**
    * 构造方法
    */
    public ArticleDO(){ }
    public ArticleDO(String articleId,String articleTitle,String createEmployeeId,String modifyEmployeeId,String articleContent,String articleType,String articleTags,String articleCategories,Integer articleHits,Integer commentsNum,Integer allowComment,Integer allowFeed,Integer delFlag,Integer articleStatus,String articleAuthor,Date createTime,Date modifyTime){
		super();
			this.createEmployeeId= createEmployeeId;
			this.modifyEmployeeId= modifyEmployeeId;
			this.articleHits= articleHits;
			this.commentsNum= commentsNum;
			this.delFlag= delFlag;
			this.articleStatus= articleStatus;
			this.createTime= createTime;
			this.modifyTime= modifyTime;
			this.articleTitle= articleTitle;
			this.articleContent= articleContent;
			this.articleType= articleType;
			this.articleTags= articleTags;
			this.articleCategories= articleCategories;
			this.allowComment= allowComment;
			this.allowFeed= allowFeed;
			this.articleAuthor= articleAuthor;
	}
    /**
     * 设置：主键ID
     */
    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }
    /**
     * 获取：主键ID
     */
    public String getArticleId() {
        return articleId;
    }

	/**
	 * 设置：创建人id
	 */
	public void setCreateEmployeeId(String createEmployeeId) {
		this.createEmployeeId = createEmployeeId;
	}
	/**
	 * 获取：创建人id
	 */
	public String getCreateEmployeeId() {
		return createEmployeeId;
	}
	/**
	 * 设置：最近修改人id
	 */
	public void setModifyEmployeeId(String modifyEmployeeId) {
		this.modifyEmployeeId = modifyEmployeeId;
	}
	/**
	 * 获取：最近修改人id
	 */
	public String getModifyEmployeeId() {
		return modifyEmployeeId;
	}
	/**
	 * 设置： 点击数量
	 */
	public void setArticleHits(Integer articleHits) {
		this.articleHits = articleHits;
	}
	/**
	 * 获取： 点击数量
	 */
	public Integer getArticleHits() {
		return articleHits;
	}
	/**
	 * 设置：评论数量
	 */
	public void setCommentsNum(Integer commentsNum) {
		this.commentsNum = commentsNum;
	}
	/**
	 * 获取：评论数量
	 */
	public Integer getCommentsNum() {
		return commentsNum;
	}
	/**
	 * 设置：是否删除
	 */
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	/**
	 * 获取：是否删除
	 */
	public Integer getDelFlag() {
		return delFlag;
	}
	/**
	 * 设置：状态
	 */
	public void setArticleStatus(Integer articleStatus) {
		this.articleStatus = articleStatus;
	}
	/**
	 * 获取：状态
	 */
	public Integer getArticleStatus() {
		return articleStatus;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：修改时间
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	/**
	 * 获取：修改时间
	 */
	public Date getModifyTime() {
		return modifyTime;
	}
	/**
	 * 设置：标题
	 */
	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}
	/**
	 * 获取：标题
	 */
	public String getArticleTitle() {
		return articleTitle;
	}
	/**
	 * 设置：内容
	 */
	public void setArticleContent(String articleContent) {
		this.articleContent = articleContent;
	}
	/**
	 * 获取：内容
	 */
	public String getArticleContent() {
		return articleContent;
	}
	/**
	 * 设置：类型
	 */
	public void setArticleType(String articleType) {
		this.articleType = articleType;
	}
	/**
	 * 获取：类型
	 */
	public String getArticleType() {
		return articleType;
	}
	/**
	 * 设置：标签
	 */
	public void setArticleTags(String articleTags) {
		this.articleTags = articleTags;
	}
	/**
	 * 获取：标签
	 */
	public String getArticleTags() {
		return articleTags;
	}
	/**
	 * 设置：分类
	 */
	public void setArticleCategories(String articleCategories) {
		this.articleCategories = articleCategories;
	}
	/**
	 * 获取：分类
	 */
	public String getArticleCategories() {
		return articleCategories;
	}
	/**
	 * 设置：开启评论
	 */
	public void setAllowComment(Integer allowComment) {
		this.allowComment = allowComment;
	}
	/**
	 * 获取：开启评论
	 */
	public Integer getAllowComment() {
		return allowComment;
	}
	/**
	 * 设置：允许反馈
	 */
	public void setAllowFeed(Integer allowFeed) {
		this.allowFeed = allowFeed;
	}
	/**
	 * 获取：允许反馈
	 */
	public Integer getAllowFeed() {
		return allowFeed;
	}
	/**
	 * 设置：作者
	 */
	public void setArticleAuthor(String articleAuthor) {
		this.articleAuthor = articleAuthor;
	}
	/**
	 * 获取：作者
	 */
	public String getArticleAuthor() {
		return articleAuthor;
	}

	@Override
	public String toString() {
		return "ArticleDO{" +
				"articleId=" + articleId +
		        ", createEmployeeId='" + createEmployeeId + '\'' +
		        ", modifyEmployeeId='" + modifyEmployeeId + '\'' +
		        ", articleHits='" + articleHits + '\'' +
		        ", commentsNum='" + commentsNum + '\'' +
		        ", delFlag='" + delFlag + '\'' +
		        ", articleStatus='" + articleStatus + '\'' +
		        ", createTime='" + createTime + '\'' +
		        ", modifyTime='" + modifyTime + '\'' +
		        ", articleTitle='" + articleTitle + '\'' +
		        ", articleContent='" + articleContent + '\'' +
		        ", articleType='" + articleType + '\'' +
		        ", articleTags='" + articleTags + '\'' +
		        ", articleCategories='" + articleCategories + '\'' +
		        ", allowComment='" + allowComment + '\'' +
		        ", allowFeed='" + allowFeed + '\'' +
		        ", articleAuthor='" + articleAuthor + '\'' +
				'}';
	}

}
