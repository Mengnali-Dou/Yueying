/**
 * 注册请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
export interface RegisterRequest {
	// 账号
	userAccount: string;

	// 用户名
	userName: string;

	// 密码
	password: string;

	// 性别 0 - 男 1 - 女
	gender: number;

	// 头像
	avatar?: string;

	// 电话
	phone?: string;

	// email
	email?: string;
}
