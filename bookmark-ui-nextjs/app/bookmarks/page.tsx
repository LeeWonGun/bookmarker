import Image from "next/image";
import styles from "./page.module.css";
import NavBar from "../components/NavBar";
import { fetchBookmarks } from "@/services/api/fetchBookmarks";
import { deflate } from "zlib";
import Bookmarks from "../components/Bookmarks";
import Pagination from "../components/Pagination";
import SearchForm from "../components/SearchForm";

// 서버 요청을 위한 컴포넌트
/*
  searchParams는 URL 쿼리 문자열을 추출하여 javascript의 객체 형태로 만들어준다

  예시 > URL: http://example.com/page?param1=value1&param2=value2 인 경우
  => searchParams는 {param1: value1, param2: value2} 와 같은 형태로 변경해줌
*/

/*
  Nextjs에서 App Router에서 Props 타입을 정의
  URL에서 Query String을 객체 형태로 전달하기 위해서 searchParams
  => page? 는 속성이 있을 수도 있고 없을 수도 있음을 의미함, 있으면 string으로 변환해준다
*/
type Props = {
  searchParams: Promise<{
    page?: string;
    query?: string;
  }>;
};

const Home = async ({ searchParams }: Props) => {

  const { page, query } = await searchParams;
  const pageNumber = page ? parseInt(page, 10):1;
  const queryString = query ? String(query) : "";

  const bookmarks = await fetchBookmarks(pageNumber, queryString); // 서버 사이트 데이터 패칭

  return (
    <>
      <h2>Welcome to Bookmark</h2>
      <SearchForm />
      <Pagination bookmarks={bookmarks} query={query} />

      <ul>
        <Bookmarks bookmarks={bookmarks} />
      </ul>
    </>
  )
}
export default Home
  