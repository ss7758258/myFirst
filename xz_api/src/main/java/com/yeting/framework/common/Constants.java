package com.yeting.framework.common;

/**
 * 参数常量
 */
public class Constants {

	/**成功*/
	public final static int    SUCCESS                 = 0;
	/**失败*/
	public final static int    ERROR                   = 1;

	public static final String SQ_COMMENT_ID        = "comm_id";
	public static final String SQ_COLLECT_ID        = "collect_id";
	public static final String SQ_FEEDBACK_ID        = "feedback_id";
	public static final String SQ_USERARTLIKE_ID        = "user_art_id";
	public static final String SQ_USERCOMMENTLIKE_ID        = "user_comment_id";
	public static final String SQ_WEIXINUSER_ID        = "weixin_user_id";
	public static final String SCHEME = "https://api.yetingfm.com";
	public static final String ANNO = "https://api.yetingfm.com/upload/anno/6aa10f61409777fe0073054d3b9273e2.png";

	public static final String COMMENT_TOPIC = "sentCommentTopic";
	public static final String WEINXIN_TOPIC = "weixinTopic";
	public static final String ART_SHARE_COUNT_TOPIC = "artShareCountTopic";
	public static final String COLLECT_TOPIC = "collectTopic";
	public static final String ART_COLLECT_COUNT_TOPIC = "artCollectCountTopic";
	public static final String ART_LIKE_COUNT_TOPIC = "artLikeCountTopic";
	public static final String USER_ART_LIKE_TOPIC = "userArtLikeTopic";
	public static final String COMMENT_LIKE_COUNT_TOPIC = "commentLikeCountTopic";
	public static final String USER_COMMENT_LIKE_TOPIC = "userCommentLikeTopic";
	public static final String FEEDBACK_TOPIC = "feedbackTopic";

	public static final String ART_UNLIKE_COUNT_TOPIC = "artUnLikeCountTopic";
	public static final String USER_ART_UNLIKE_TOPIC = "userArtUnLikeTopic";
	public static final String COMMENT_UNLIKE_COUNT_TOPIC = "commentUnLikeCountTopic";
	public static final String USER_COMMENT_UNLIKE_TOPIC = "userCommentUnLikeTopic";
	public static final String ART_UNCOLLECT_COUNT_TOPIC = "artUnCollectCountTopic";
	public static final String UNCOLLECT_TOPIC = "uncollectTopic";

	public static final String DELETE_COLLECT_TOPIC = "deleteCollectTopic";
	public static final String SETCOMMENT_TOPIC = "setCommentTopic";
	public static final String DELETE_COMMENT_TOPIC = "deleteCommentTopic";

	public static final String UPDATE_MAINREADCOUNT_TOPIC = "updateMainReadCountTopic";

	public static final String INSERT = "INSERT";
	public static final String UPDATE = "UPDATE";
	public static final String DELETE = "DELETE";

	public static final String REDIS_MAIN_TOPIC = "redisMainTopic";
	public static final String REDIS_DETAIL_TOPIC = "redisDetailTopic";
	public static final String REDIS_MAIN_READ_TOPIC = "redisMainReadTopic";
	public static final String REDIS_COMMENT_TOPIC = "redisCommentTopic";
	public static final String REDIS_SAVECOMMENT_TOPIC = "redisSaveCommentTopic";
	public static final String REDIS_ARTSHARE_TOPIC = "redisArtShareTopic";
	public static final String REDIS_ARTCOLLECT_TOPIC = "redisArtCollectTopic";
	public static final String REDIS_ARTLIKE_TOPIC = "redisArtLikeTopic";
	public static final String REDIS_MAIN_LIKE_TOPIC = "redisMainLikeTopic";
	public static final String REDIS_COMMENT_LIKE_TOPIC = "redisCommentLikeTopic";
	public static final String REDIS_ARTUNLIKE_TOPIC = "redisArtUnLikeTopic";
	public static final String REDIS_MAIN_UNLIKE_TOPIC = "redisMainUnLikeTopic";
	public static final String REDIS_COMMENT_UNLIKE_TOPIC = "redisCommentUnLikeTopic";
	public static final String REDIS_ARTUNCOLLECT_TOPIC = "redisArtUnCollectTopic";
	public static final String REDIS_COMMENTDELETE_TOPIC = "redisCommentDeleteTopic";

	public static final String REDIS_HOTCOMMENT_TOPIC = "redisHotCommentTopic";


	public static final String KAFKA_FLAG = "@";
}
