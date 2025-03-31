/**
 * 更新用户信息请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
export interface UpdateUserInfoRequest {
	// id
	userId: number;

	// 用户名
	userName: string;

	// 账号
	userAccount: string;

	// 头像
	avatarUrl: string;

	// 性别
	gender: number;

	// 电话
	phone: string;

	// email
	email: string;
}
