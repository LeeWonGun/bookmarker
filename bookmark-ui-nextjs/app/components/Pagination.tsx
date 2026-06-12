import React from 'react'
import { BookmarksResponse } from '../types/bookmark'
import Link from 'next/link';


// Props에 BookmarksResponse 안에 page에 관련된 정보가 들어있으므로 타입 정의
interface PaginationProps {
    bookmarks: BookmarksResponse;
    query?: string // 선택적 properties "?:" 있어도 되고 없어도 됨
}

const Pagination = ({ bookmarks, query }: PaginationProps) => {

    const path ="/bookmarks";
    const firstPage = {pathname: path, query: {page:1}};
    const lastPage = {pathname: path, query: {page:bookmarks.totalPages}};
    const previousPage = {pathname: path, query: {page:bookmarks.currentPage-1}};
    const nextPage = {pathname: path, query: {page:bookmarks.currentPage+1}};

    return (
        <div>
            <nav aria-label="Page navigation example">
                <ul className="pagination justify-content-center">
                    {/* 이전 */}
                    <li className={`page-item ${bookmarks.hasPrevious ? "" : "disabled"}`} >
                        <Link className="page-link" href={previousPage}>Previous</Link>
                    </li>

                    {/* 이후 */}
                    <li className={`page-item ${bookmarks.hasNext ? "" : "disabled"}`}>
                        <Link className="page-link" href={nextPage}>Next</Link>
                    </li>
                </ul>
            </nav>
        </div>
    )
}

export default Pagination