import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * SWEA 2382 : [모의 SW 역량테스트] 미생물 격리
 * 정사각형 구역 안에 K개의 미생물 군집이 있다.
 * 이 구역은 가로 N개, 세로 N개, 총 N * N 개의 동일한 크기의 정사각형 셀들로 이루어져 있다.
 * 미생물들이 구역을 벗어나는걸 방지하기 위해, 가장 바깥쪽 가장자리 부분에 위치한 셀들에는 특수한 약품이 칠해져 있다.
 * ① 최초 각 미생물 군집의 위치와 군집 내 미생물의 수, 이동 방향이 주어진다. 약품이 칠해진 부분에는 미생물이 배치되어 있지 않다. 이동방향은 상, 하, 좌, 우 네 방향 중 하나이다.
 * ② 각 군집들은 1시간마다 이동방향에 있는 다음 셀로 이동한다.
 * ③ 미생물 군집이 이동 후 약품이 칠해진 셀에 도착하면 군집 내 미생물의 절반이 죽고, 이동방향이 반대로 바뀐다.
 * 		미생물 수가 홀수인 경우 반으로 나누어 떨어지지 않으므로, 다음과 같이 정의한다.
 * 		살아남은 미생물 수 = 원래 미생물 수를 2로 나눈 후 소수점 이하를 버림 한 값
 * 		따라서 군집에 미생물이 한 마리 있는 경우 살아남은 미생물 수가 0이 되기 때문에, 군집이 사라지게 된다.
 * ④ 이동 후 두 개 이상의 군집이 한 셀에 모이는 경우 군집들이 합쳐지게 된다.
 * 		합쳐 진 군집의 미생물 수는 군집들의 미생물 수의 합이며, 이동 방향은 군집들 중 미생물 수가 가장 많은 군집의 이동방향이 된다.
 * 		합쳐지는 군집의 미생물 수가 같은 경우는 주어지지 않으므로 고려하지 않아도 된다.
 * M 시간 동안 이 미생물 군집들을 격리하였다. M시간 후 남아 있는 미생물 수의 총합을 구하여라.
 */
public class SWEA2382_미생물격리 {
	static int N;
	static int M;
	static int K;
	
	static int[] dr = {0, -1, 1, 0, 0};
	static int[] dc = {0, 0, 0, -1, 1};

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		
		for(int tc = 1; tc <= T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			// 구역 크기, 미생물 수, 방치 시간
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			// 미생물 정보 입력
			Queue<Microbe> q = new LinkedList<Microbe>();
			for(int i = 0; i < K; i++) {
				st = new StringTokenizer(br.readLine());
				
				int r = Integer.parseInt(st.nextToken());
				int c = Integer.parseInt(st.nextToken());
				int num = Integer.parseInt(st.nextToken());
				int dir = Integer.parseInt(st.nextToken());
				
				q.offer(new Microbe(r, c, num, dir));
			}
			
			while(M > 0) {
				int size = q.size();
				PriorityQueue<Microbe> pq = new PriorityQueue<Microbe>();
				// 미생물 이동
				for(int i = 0; i < size; i++) {
					Microbe cur = q.poll();
					
					cur.move();
					// 이동 후 같은 위치의 미생물을 합하기 위하여 우선순위 큐에 삽입
					pq.offer(cur);
				}
				// 현재 미생물
				Microbe cur = pq.poll();
				while(!pq.isEmpty()) {
					// 위치가 같지 않으면
					if(cur.r != pq.peek().r || cur.c != pq.peek().c) {
						// 더 할 때 max 값이 바뀔수도 있으니 그런 경우 자기 자신으로 초기화 해줌
						cur.max = cur.num;
						q.add(cur);
						cur = pq.poll();
						continue;
					}
					
					Microbe next = pq.poll();
					cur.addMicobe(next);
				}
				// 마지막 미생물이 cur 에 남아잇으므로 큐에 삽입
				cur.max = cur.num;
				q.offer(cur);
				// 시간 감소
				M--;
			}
			// 남아 있는 미생물 수의 합 구하기
			int answer = 0;
			while(!q.isEmpty())
				answer += q.poll().num;
			
			// 결과 출력
			System.out.format("#%d %d\n", tc, answer);
		}
		
		br.close();
	}

	static class Microbe implements Comparable<Microbe> {
		int r;
		int c;
		int num;
		int dir;
		int max;
		
		public Microbe(int r, int c, int num, int dir) {
			this.r = r;
			this.c = c;
			this.num = num;
			this.dir = dir;
			this.max = this.num;
		}
		// 미생물 이동, 격자의 가장자리에 도착하면 미생물의 수가 반으로 줄어 들고 방향 바꿈
		public void move() {
			r = r + dr[dir];
			c = c + dc[dir];
			// 위로 이동하다 약품에 위치 한것이므로 방향을 아래로 바꿈
			if(r == 0) {
				num /= 2;
				dir = 2;
			} else if(r == N - 1) {
				num /= 2;
				dir = 1;
			} else if(c == 0) {
				num /= 2;
				dir = 4;
			} else if(c == N - 1) {
				num /= 2;
				dir = 3;
			}
		}
		// 같은 위치에 있는 미생물을 더 함
		public void addMicobe(Microbe m) {
			// 더 하면서 가장 컸던 값을 max에 저장하고 이를 이용하여 방향 설정
			if(this.max < m.num) {
				this.dir = m.dir;
				this.max = m.max;
			}
			this.num += m.num;
		}
		// 우선순위 큐에서 왼쪽 위에서 부터 아래쪽으로 내려오게 정렬
		@Override
		public int compareTo(Microbe o) {
			if(this.r == o.r)
				return this.c - o.c;
			return this.r - o.r;
		}
	}
}
