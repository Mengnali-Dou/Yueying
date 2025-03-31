export const getDate = (timeStamp: string): string => {
	const date = new Date(timeStamp);

	const year = date.getFullYear();
	const month = (date.getMonth() + 1).toString().padStart(2, "0");
	const day = date.getDate().toString().padStart(2, "0");

	return `${year}-${month}-${day}`;
};

export const getTime = (timeStamp: string): string => {
	const date = new Date(timeStamp);

	const hours = date.getHours().toString().padStart(2, "0");
	const minutes = date.getMinutes().toString().padStart(2, "0");
	const seconds = date.getSeconds().toString().padStart(2, "0");

	return `${hours}:${minutes}:${seconds}`;
};

export const getDateTime = (timeStamp: string): string => {
	const date = new Date(timeStamp);

	const year = date.getFullYear();
	const month = (date.getMonth() + 1).toString().padStart(2, "0");
	const day = date.getDate().toString().padStart(2, "0");
	const hours = date.getHours().toString().padStart(2, "0");
	const minutes = date.getMinutes().toString().padStart(2, "0");
	const seconds = date.getSeconds().toString().padStart(2, "0");

	return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
};

export const getHour = (timeStamp: string): string => {
	const date = new Date(timeStamp);

	return date.getHours().toString().padStart(2, "0");
};
