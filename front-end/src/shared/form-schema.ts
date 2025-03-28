import { Rule } from "ant-design-vue/es/form";

/**
 * 密码格式校验
 * @param _rule
 * @param password
 */
export const passwordCheck = async (_rule: Rule, password: string) => {
	if (!password) {
		return Promise.reject("请输入密码");
	}
	if (password.length < 8 || password.length > 20) {
		return Promise.reject("密码长度应该为8-20位");
	}
	const regx = /^(?=.*\d)(?=.*[a-zA-Z])(?=.*[^\da-zA-Z\s]).{8,20}$/;
	if (!regx.test(password)) {
		return Promise.reject("密码强度不足");
	}
	await Promise.resolve();
};

/**
 * 账号格式校验
 * @param _rule
 * @param account
 */
export const userAccountCheck = async (_rule: Rule, account: string) => {
	if (!account) {
		return Promise.reject("请输入账号");
	}
	if (account.length < 4) {
		return Promise.reject("账号长度不小于4位");
	}
	const regx = /[^a-zA-Z0-9-_]/;
	if (regx.test(account)) {
		return Promise.reject("账号不能包含特殊字符");
	}
	await Promise.resolve();
};
