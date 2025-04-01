/**
 * 影片信息
 */
export interface MovieInfoViewModel {
	// id
	movieId: number;

	// 影片名
	movieName: string;

	// 影片类型
	movieType: string;

	// 影片封面--大
	movieCoverLarge?: string;

	// 影片封面--小
	movieCoverSmall?: string;

	// 上映时间
	releaseDate: string;

	// 影片时长
	movieDuration: string;

	// 主要演员
	mainActor?: string;

	// 影片简介
	movieProfile?: string;
}
