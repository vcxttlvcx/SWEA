import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
/**
 * SWEA 1227. 미로2
 * 주어진 미로의 출발점으로부터 도착점까지 갈 수 있는 길이 있는지 판단하는 프로그램 작성
 * 가능할 시 1, 불가능 할 시 0 출력
 * 미로에서 0은 길, 1은 벽, 2는 출발점, 3은 도착점을 나타낸다
 */
public class Miro2 {

	public static void main(String[] args) {
//		파일 입출력을 위한 try-catch문, 제출 시 주석 처리 필수
		try {
			System.setIn(new FileInputStream("input.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
//		표준입력 System.in 으로부터 스캐너를 만들어 데이터를 읽어옵니다.
		Scanner sc = new Scanner(System.in);
//		Testcase의 개수
		int T = 10;
//		T = sc.nextInt();

		for(int test_case = 1; test_case <= T; test_case++) {
//			몇번째 테스트케이스인지 입력 받음
			int tc = sc.nextInt();
//			miro를 저장할 배열과 방문 체크를 할 배열 선언
			char[][] miro = new char[100][100];
			boolean[][] isVisited = new boolean[100][100];
//			미로의 시작점을 저장할 변수 선언
			Point start = null;
//			상, 하, 좌, 우 순으로 이동하기 위한 배열
			int[] dr = {-1, 1, 0, 0};
			int[] dc = {0, 0, -1, 1};
//			미로의 정보를 입력받고 시작점과 끝점을 찾는다
			for(int i = 0; i < 100; i++) {
				miro[i] = sc.next().toCharArray();
				for(int j = 0; j < 100; j++) {
					if(miro[i][j] == '2')
						start = new Point(i, j);
				}
			}
//			최종 출력을 위한 StringBuilder의 선언와 출력 양식 초기화
			StringBuilder sb = new StringBuilder();
			sb.append("#").append(tc).append(" ");
//			bfs를 위한 큐의 선언과 시작점 방문 및 방문 체크
			Queue<Point> queue = new LinkedList<Point>();
			isVisited[start.r][start.c] = true;
			queue.add(start);
//			찾았는지 여부를 확인할 boolean 변수 선언
			boolean flag = false;
//			bfs로 탐색하며 미로의 종료 지점까지 진행 가능한지 확인
			loop:while(!queue.isEmpty()) {
//				현재 방문하는 위치
				Point now = queue.poll();
//				4방위 탐색 진행
				for(int i = 0; i < 4; i++) {
					int nr = now.r + dr[i];
					int nc = now.c + dc[i];
//					범위를 벗어나거나, 벽이거나 이미 방문하였으면 방문하지 않음
					if(nr < 0 || nc < 0 || nr >= 100 || nc >= 100 || miro[nr][nc] == '1' || isVisited[nr][nc])
						continue;
//					다음 방문한 위치가 종료 지점이라면 while문 탈출
					if(miro[nr][nc] == '3') {
						flag = true;
						break loop;
					}
//					다음 방문할 위치를 큐에 저장 및 방문 체크
					isVisited[nr][nc] = true;
					queue.add(new Point(nr, nc));
				}
			}
//			미로의 종료 지점에 닿을 수 있다면 1 없다면 0 출력
			if(flag)
				sb.append("1");
			else
				sb.append("0");
			System.out.println(sb.toString());
		}	// end test_case
		
		sc.close();
	}

}
// 미로에서 현재 위치를 저장하기 위한 클래스
class Point {
//	행과 열을 저장하기 위한 변수 선언
	int r;
	int c;
//	생성자를 통해 객체 초기화
	public Point(int r, int c) {
		this.r = r;
		this.c = c;
	}
}
