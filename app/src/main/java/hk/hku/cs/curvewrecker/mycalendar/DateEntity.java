package hk.hku.cs.curvewrecker.mycalendar;

import java.io.Serializable;

/**
 * @author daij
 * @version 1.0 日历实体类，添加的参数在获取日历实体集合的时候设置
 */
public class DateEntity implements Serializable {

	private static final long serialVersionUID = -6053739977785155088L;
	/** 年 */
	public int year;
	/** 月 */
	public int month;
	/** 日 */
	public int day;
	/** 星期 */
	public String weekDay;
	/** 是否为当前日期 */
	public boolean isNowDate;
	/** 是否为本月日期 */
	public boolean isSelfMonthDate;

	//是否是第一名
	public boolean isFirst;

	//是否有星星 -- 学习到足够时间
	public boolean isStar;

	//是否有月亮 -- 睡觉到足够时间
	public boolean isMoon;
}
