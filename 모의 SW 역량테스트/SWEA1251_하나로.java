import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * SWEA 1251 : 하나로
 * 모든 섬을 해저 터널로 연결하는 것이 목표 두 해저 터널이 교차되더라도 물리적으로는 연결되지 않은 것으로 가정
 * 직접적으로 연결되어도 되지만, 간접적으로 연결되어도 됨 해저터널 건설로 인한 파괴되는 자연을 위해 다음과 같은 환경 부담금 정책이 있음
 * 환경 부담 세열(E)과 각 해저터널 길이(L)의 제곱의 곱(E * L^2)만틈 지불 총 환경 부담금을 최소로 지불하며, N개의 모든 섬을
 * 연결할 수 있는 교통 시스템 설계
 */
public class SWEA1251_하나로 {
	static int N;
	static Island[] island;

	static double E;

	static boolean[] visited;

	static long answer;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int T = Integer.parseInt(br.readLine());
		// 테스트 케이스 시작
		for (int tc = 1; tc <= T; tc++) {
			answer = Long.MAX_VALUE;

			N = Integer.parseInt(br.readLine());
			island = new Island[N];
			// X 좌표와 Y 좌표를 읽어옴
			StringTokenizer stX = new StringTokenizer(br.readLine());
			StringTokenizer stY = new StringTokenizer(br.readLine());
			// 읽은 x, y 좌표를 이용해 섬의 좌표 완성
			for (int i = 0; i < N; i++) {
				int x = Integer.parseInt(stX.nextToken());
				int y = Integer.parseInt(stY.nextToken());

				island[i] = new Island(x, y);
			}
			// 환경 부담 세율 입력
			E = Double.parseDouble(br.readLine());
			// 방문 체크를 위한 배열 생성
			visited = new boolean[N];
			// Prim 알고리즘을 이용하여 MST 생성
			answer = solve();

			// 결과 출력, 결과값을 소수첫째자리에서 반올림하기 위해 Math.round 메소드 사용
			System.out.format("#%d %d\n", tc, Math.round(answer * E));
		}

		br.close();
	}
	// Prim 알고리즘
	public static long solve() {
		// 현재 가장 가까운 거리를 가지는 정점을 저장하기 위한 변수
		int minVertex = 0;
		// 현재 거리의 최소값과 최소 환경부담금을 저장하기 위한 변수
		long min, result = 0;
		// 접근 가능한 정점으로 갈 수 있는 최소 비용을 저장하기 위한 배열
		long[] minEdge = new long[N];
		for(int i = 0; i < N; i++)
			minEdge[i] = Long.MAX_VALUE;
		minEdge[0] = 0;

		for (int c = 0; c < N; c++) {
			min = Long.MAX_VALUE;
			minVertex = 0;
			// 현재 정점에서 방문하지 않은 정점들 중 갈 수 있는 최소 거리에 있는 정점 선택
			for(int i = 0; i < N; i++) {
				if(!visited[i] && minEdge[i] < min) {
					min = minEdge[i];
					minVertex = i;
				}
			}
			// 거리를 더 하고 방문 체크
			result += min;
			visited[minVertex] = true;
			// 선택한 정점에서 갈 수 있는 정점들을 포함하여 다음 정점으로 갈 수 있는 최소거리 배열 초기화
			for(int i = 0; i < N; i++) {
				long dist = calcDist(minVertex, i);
				
				if(!visited[i] && dist < minEdge[i])
					minEdge[i] = dist;
			}
		}
		
//		System.out.println(result);
		return result;
	}
	// 현재 섬과 다음 섬간의 거리를 구함 어짜피 제곱할 것이니 루트를 씌운 값이 아닌 값으로 반환
	public static long calcDist(int i, int j) {
		long x = island[i].x - island[j].x;
		long y = island[i].y - island[j].y;
		return x * x + y * y;
	}
	// 섬의 좌표를 저장하기 위한 클래스 정의
	static class Island {
		long x;
		long y;

		public Island(long x, long y) {
			super();
			this.x = x;
			this.y = y;
		}
	}

}
