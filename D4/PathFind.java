import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
/**
 * SWEA 1219. [S/W 문제해결 기본] 4일차 - 길찾기
 * A도시에서 출발하여 B도시로 가는 길이 존재하는지 조사
 * BFS를 이용하여 풀이
 */
public class PathFind {

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
			int tc = sc.nextInt();	// 테스트 케이스 번호
			int numOfPath = sc.nextInt();	// 길의 개수
			int ans = 0;	// 길의 존재 여부 출력 할 변수
//			길을 저장할 배열, 한 정점에서 갈 수 있는 길의 개수 최대 2개
//			배열 두개를 통하여 모든 길을 저장
			int[][] path = new int[2][100];
//			정점 방문 체크를 통해 중복 방문 방지
			boolean[] isVisited = new boolean[100];
//			길에 대한 정보 입력 받음
			for(int i = 0; i < numOfPath; i++) {
				int p = sc.nextInt();
				int c = sc.nextInt();
//				앞서 입력 받은 길이 없다면 0에 저장 있다면 1에 저장
				if(path[0][p] == 0)
					path[0][p] = c;
				else
					path[1][p] = c;
			}
//			최종 출력을 위한 StringBuilder의 선언와 출력 양식 초기화
			StringBuilder sb = new StringBuilder();
			sb.append("#").append(tc).append(" ");
//			BFS 진행을 위한 큐 생성 후 시작점 삽입 및 방문 체크
			Queue<Integer> queue = new LinkedList<Integer>();
			isVisited[0] = true;
			queue.add(0);
//			BFS 진행을 통해 끝점 도달 가능한지 여부 확인
			while(!queue.isEmpty()) {
				int now = queue.poll();
//				도착점에 도착 시 1로 초기화 후 while문 탈출
				if(now == 99) {
					ans = 1;
					break;
				}
//				방문 하지 않은 위치 이면 방문
				if(!isVisited[path[0][now]]) {
					isVisited[path[0][now]] = true;
					queue.add(path[0][now]);					
				}
//				다른 길이 있고 방문 하지 않았을 경우 방문
				if(!isVisited[path[1][now]]) {
					isVisited[path[1][now]] = true;
					queue.add(path[1][now]);
				}
			}
//			최종 결과 출력
			sb.append(ans);
			System.out.println(sb.toString());
		}	// end test_case
		
		sc.close();
	}

}
