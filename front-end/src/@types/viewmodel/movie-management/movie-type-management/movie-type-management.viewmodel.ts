/**
 * 影片类型信息
 */
export interface MovieTypeManagementViewModel {
	// 影片类型id
	movieTypeId: number;

	// 影片类型
	movieType: string;

	// 是否修改
	isUpdate: boolean;
}

/**
 * 影片类型
 */
export interface MovieTypeInfoViewModel {
	// 影片类型id
	movieTypeId: number;

	// 影片类型
	movieType: string;
}
