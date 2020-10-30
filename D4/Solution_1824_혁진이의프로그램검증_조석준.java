import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * SWEA 1825 : 혁진이의 프로그램 검증
 * 
 * 복잡한 시뮬레이션
 * 무한으로 반복되는지 판단 => 같은 좌표에 같은 생태로 진입 시
 * 		좌표에 진입 시 상태를 저장, 동일한 상태로 진입한 것이라면 무한루프라고 판단
 * 이동 방향을 상하좌우 중 무작위로 하나 선택 => 모두 해봐야함
 * 		갈림길 저장해두자, DFS 처럼 스택에 저장해두고 사용
 */
public class Solution_1824_혁진이의프로그램검증_조석준 {
	// 상 하 좌 우
	static int[] dr = { -1, 1, 0, 0 };
	static int[] dc = { 0, 0, -1, 1 };
	static int R;
	static int C;

	static boolean[][][][] visited;

	public static void main(String args[]) throws Exception {
		Scanner sc = new Scanner(System.in);

		int T;
		T = sc.nextInt();

		for (int test_case = 1; test_case <= T; test_case++) {
			// 행과 열 크기 입력
			R = sc.nextInt();
			C = sc.nextInt();
			// 프로그램 입력
			char[][] pg = new char[R][];
			for (int i = 0; i < R; i++) {
				String input = sc.next();
				pg[i] = input.toCharArray();
			}
			// 정수를 저장할 메모리
			int memory = 0;
			
			String answer = "NO";
			Queue<Point> q = new LinkedList<Point>();
			// 방향과 메모리 값을 이용한 4차원 방문 체크 배열
			visited = new boolean[R][C][16][4];
			// 시작 위치가 숫자이면 숫자로 입력
			if ('0' <= pg[0][0] && pg[0][0] <= '9')
				memory = pg[0][0] - '0';
			// 시작점 bfs 탐색에 입력
			q.add(new Point(0, 0, 3, memory));
			visited[0][0][memory][3] = true;
			// bfs 탐색
			loop: while (!q.isEmpty()) {
				Point now = q.poll();
				// 조건 탐색
				switch (pg[now.r][now.c]) {
				case '^': // 방향을 위로 이동
					now.dir = 0;
					break;
				case 'v': // 방향을 아래로 이동
					now.dir = 1;
					break;
				case '<': // 방향을 왼쪽으로 이동
					now.dir = 2;
					break;
				case '>': // 방향을 오른쪽으로 이동
					now.dir = 3;
					break;
				case '_': // memory가 0이면 오른쪽 아니면 왼쪽
					now.dir = now.memory == 0 ? 3 : 2;
					break;
				case '|': // memory가 0이면 아래쪽 아니면 위쪽
					now.dir = now.memory == 0 ? 1 : 0;
					break;
				case '.': // 아무 동작도 하지 않음
					break;
				case '@': // 프로그램 정지
					answer = "YES";
					break loop;
				case '+': // 메모리에 1을 더함
					now.memory = now.memory + 1 > 15 ? 0 : now.memory + 1;
					break;
				case '-': // 메모리에 1을 뺀다
					now.memory = now.memory - 1 < 0 ? 15 : now.memory - 1;
					break;
				case '?':	// 방향 bfs 진행 - 아랫쪽 코드에서 진행함
					break;
				default: // 모두 해당안되는 숫자인 경우1
					now.memory = pg[now.r][now.c] - '0';
					break;
				} // end switch
				// 4방향을 모두 bfs 탐색하여 정지 지점까지 갈 수 있는지 탐색
				if (pg[now.r][now.c] == '?') {
					for (int i = 0; i < 4; i++) {
						// 다음 지점으로 이동 - 이미 방문 했으면 null 값
						Point next = nextPosition(now.r, now.c, now.memory, i);
						// 다음 지점이 이미 방문한 위치라면
						if (next == null)
							continue;
						
						visited[next.r][next.c][next.memory][next.dir] = true;
						q.add(new Point(next.r, next.c, next.dir, next.memory));
					}
				} else {
					// 다음 지점으로 이동 - 이미 방문 했으면 null 값
					Point next = nextPosition(now.r, now.c, now.memory, now.dir);
					// 다음 지점이 이미 방문한 위치라면
					if (next == null)
						continue;

					visited[next.r][next.c][next.memory][next.dir] = true;
					q.add(new Point(next.r, next.c, next.dir, next.memory));
				}
			}
			// 결과 출력
			System.out.println("#" + test_case + " " + answer);
		}

		sc.close();
	}
	// 다음 탐색 지점 반환, 이미 방문 한 위치라면 null 값
	public static Point nextPosition(int r, int c, int memory, int dir) {
		Point p = null;

		int nr = r + dr[dir];
		int nc = c + dc[dir];

		if (nr >= R)
			nr = 0;
		else if (nr < 0)
			nr = R - 1;
		if (nc >= C)
			nc = 0;
		else if (nc < 0)
			nc = C - 1;

		if (!visited[nr][nc][memory][dir])
			p = new Point(nr, nc, dir, memory);

		return p;
	}

	static class Point {
		int r;
		int c;
		int dir;
		int memory;

		public Point(int r, int c, int dir, int memory) {
			this.r = r;
			this.c = c;
			this.dir = dir;
			this.memory = memory;
		}

	}

}
