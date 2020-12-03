import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * SWEA 2383 : [모의 SW 역량테스트] 점심 식사시간
 * 
 * 1. 각 모든 사람별로 각 계단까지의 거리를 기억 2. 각 계단이 각 사람들이 자신까지의 거리를 기억 3. 별도의 자료공간을 준비
 * 
 * 계단이 2개 뿐이기 때문에 1번이 유리
 */
public class SWEA2383_점심식사시간 {
	private static int N;
	private static int ans;
	
	private static int[][] map;

	private static List<Stair> stairs;
	private static List<Person> people;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		int T = sc.nextInt();
		for (int tc = 1; tc <= T; tc++) {
			N = sc.nextInt(); // 방의 한 변의 길이

			map = new int[N][N];
			stairs = new ArrayList<Stair>();
			people = new ArrayList<Person>();

			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					map[i][j] = sc.nextInt();
					// 1이면 사람, 아니면 계단
					if (map[i][j] == 0)
						continue;
					else if (map[i][j] == 1)
						people.add(new Person(i, j));
					else
						stairs.add(new Stair(i, j, map[i][j]));
				}
			}
			// 각 사람별로 각 계단까지의 거리 계산
			for (int i = 0; i < people.size(); i++) {
				for (int j = 0; j < stairs.size(); j++) {
					// i번 사람과 j번 계단 사이의 거리를 구해서 i번 사람의 dist[j]에다가 입력
					people.get(i).dist[j] = Math.abs(people.get(i).r - stairs.get(j).r) 
							+ Math.abs(people.get(i).c - stairs.get(j).c) + 1;
				}
			}
			ans = Integer.MAX_VALUE;
			powerSet(0);
			System.out.println("#" + tc + " " + ans);
		}

		sc.close();
	}
	// i번째 사람에 대해서 계단을 배정
	static void powerSet(int idx) {
		if(idx == people.size()) {
			int[][] stairTimeTable = new int[2][200];
			PriorityQueue<Person>[] stairQueue = new PriorityQueue[2];
			stairQueue[0] = new PriorityQueue<Person>();
			stairQueue[1] = new PriorityQueue<Person>();
			for (Person person : people)
				stairQueue[person.sel].add(person);
			int max = 0;
			// 모든 계단에 대해서
			for (int i = 0; i < stairs.size(); i++) {
				// 빨리 도착하는 사람부터 꺼냄
				int to = 0;
				while(!stairQueue[i].isEmpty()) {
					Person p = stairQueue[i].poll();
					// 선택된 계단까지 거리부터 계단을 내려가기 시작
					int from = p.dist[p.sel];
					// 도착부터, 내가 선택된 계단의 높이만큼 계단에 머물음
					to = from + stairs.get(p.sel).height;
					for(int j = from; j < to; j++) {
						if(stairTimeTable[p.sel][j] == 3) {
							to++;
							continue;
						}
						stairTimeTable[p.sel][j]++;
					}
				}
				if(to > max)
					max = to;
			}
			if(ans > max)
				ans = max;
			
			return;
		}
		people.get(idx).sel = 0;
		powerSet(idx + 1);
		people.get(idx).sel = 1;
		powerSet(idx + 1);
	}

	static class Person implements Comparable<Person> {
		int r, c;
		int[] dist;
		int sel;	// 배정된 계단의 번호

		Person(int r, int c) {
			this.r = r;
			this.c = c;
			dist = new int[2];
		}

		@Override
		public int compareTo(Person o) {
			return dist[sel] - o.dist[o.sel];
		}
	}

	static class Stair {
		int r, c, height;

		Stair(int r, int c, int height) {
			this.r = r;
			this.c = c;
			this.height = height;
		}
	}
}
