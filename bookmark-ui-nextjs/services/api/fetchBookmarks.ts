import { BookmarksResponse } from '@/app/types/bookmark';
import axios from 'axios';
import React from 'react'
// const API_BASE_URL = "http://localhost:8080";
// const API_BASE_URL = "http://host.docker.internal:8080";

const getApiUrl = () => {
    const serverApiUrl = process.env.SERVER_SIDE_API_BASE_URL;
    const clientApiUrl = process.env.NEXT_PUBLIC_CLIENT_SIDE_API_BASE_URL;

    if(typeof window === 'undefined'){
        console.log('서버사이드 실행 - 사용 URL:' , serverApiUrl)
        return serverApiUrl || clientApiUrl;
    }
    console.log('클라이언트 사이드 실행 사용URL:', clientApiUrl);
    return clientApiUrl;
}

export const fetchBookmarks = async (page: number, query: string): Promise<BookmarksResponse> => {
    let apiUrl = getApiUrl();
    const res = await axios.get<BookmarksResponse>(`${apiUrl}/api/bookmarks?page=${page}&query=${query}`);
    return res.data;
}

export const saveBookmark = async (bookmark: { title: string, url: string }) => {
    let apiUrl = getApiUrl();
    try {
        const resp = await axios.post(`${apiUrl}/api/bookmarks`, bookmark);
        return resp.data;

    } catch (error) {
        console.log("Error Saving Bookmark", error);
        throw new Error("북마크 저장에 실패하였습니다");
    }

}