import React from 'react'
// 컴포넌트의 이름과 타입의 이름이 같다면 import시 Conflict가 발생한다
// import에서 "type" 을 지정하면 import 타입을 정의한다
import type { Bookmark } from '../types/bookmark';
import Link from 'next/link';

// 전달받은 Props 타입 정의
interface BookmarkProps{
    bookmark: Bookmark;
}


const Bookmark = ({ bookmark }: BookmarkProps) => {
  return (
    <div>
        <div className='alert alert-primary' role='alert'>
            <h5>
                <Link href={bookmark.url}>{bookmark.title}</Link>
            </h5>
        </div>
    </div>
  )
}

export default Bookmark