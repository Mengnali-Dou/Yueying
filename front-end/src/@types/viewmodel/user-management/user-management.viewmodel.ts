/**
 * 用户信息
 */
export interface UserInfoViewModel {
	// 用户id
	userId: number;

	// 用户名
	userName: string;

	// 用户账号
	userAccount: string;

	// 用户头像
	avatarUrl: string;

	// 用户性别
	gender: string;

	// 电话
	phone: string;

	// email
	email: string;

	// 账号创建时间
	createTime: string;

	// 用户角色 0 - 普通用户 1 - 管理员 2 - 影院管理员 3 - 活动管理员
	userRole: number;

	// 用户账号状态 0 - 正常 1 - 密码重置 2 - 账号注销
	userStatus: number;
}

/**
 * 搜索用户信息
 */
export interface SearchUserViewModel {
	// 账号
	userAccount?: string;

	// 用户名
	userName?: string;
}

/**
 * 注册用户信息
 */
export interface RegisterViewModel {
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
