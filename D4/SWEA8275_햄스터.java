import java.util.Scanner;

/**
 * SWEA 8275 : 햄스터(D4)
 * 
 * 정우는 햄스터를 많이 기르고 있는데, 햄스터들에게 별 관심을 가지고 있지는 않다.
 * 정우는 햄스터 우리를 N개 가지고 있으며, 각 우리에 1번에서 N번까지의 번호를 붙여 일렬로 놔두고 있다.
 * 정우는 햄스터들에게 별 관심이 없지만, 각 우리에 0마리 이상 X마리 이하의 햄스터가 있다는 것은 알고 있다.
 * 어느 날 경근이가 정우 집에 놀러 왔다.
 * 경근이는 바쁜 정우와 노는 대신 햄스터의 수를 세면서 놀았다.
 * 경근이는 M개의 기록을 남겼는데, 각 기록은 “l번 우리에서 r번 우리까지의 햄스터 수를 세었더니 s마리였다.” 하는 내용들이다.
 * 경근이가 남긴 기록을 모두 만족하는 햄스터 수 배치를 구하는 프로그램을 작성하라.
 */
public class SWEA8275_햄스터 {
	private static int N;		// 햄스터 우리의 수
	private static int X;		// 각 우리에 있을 수 있는 햄스터의 최대 마리 수
	private static int M;		// 경근이가 남긴 기록의 수
	private static int max;		// 지금까지 알려진 최대 햄스터 수
	private static int[] ans;
	
	private static int[] cage;
	private static int[] sum;	// 누적합
	
	static class Record {
		int l, r, s;
		 public Record(int l, int r, int s) {
			 this.l = l;
			 this.r = r;
			 this.s = s;
		}
	}
	
	private static Record[] record;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		
		for (int tc = 1; tc <= T; tc++) {
			N = sc.nextInt();	// 햄스터 우리의 수
			X = sc.nextInt();	// 각 우리에 있을 수 있는 햄스터의 최대 마리 수
			M = sc.nextInt();	// 경근이가 남긴 기록의 수
			
			ans = new int[N + 1];
			cage = new int[N + 1];
			sum = new int[N + 1];
			record = new Record[M];
			// 햄스터 전체가 0마리 일수도 있음
			max = -1;
			for(int i = 0; i < M; i++) {
				record[i] = new Record(sc.nextInt(), sc.nextInt(), sc.nextInt());
			}
			
			solve(1);
			
			StringBuilder sb = new StringBuilder("#" + tc + " ");
			if(max == -1)
				sb.append(max);
			else {
				for(int i = 1; i <= N; i++)
					sb.append(ans[i] + " ");
			}
			
			System.out.println(sb.toString());
		}
		
		sc.close();
	}

	public static void solve(int idx) {
		// idx가 N번째 집까지 완료한 후, N + 1에 도달하면 기저 파트
		if(idx > N) {
			// datas 배열에 들어있는 햄스터 마리수에 일치하는 조합인지 검사
			for (int i = 0; i < M; i++) {
				// i번째 기록이 case구성이 만족하지 않는다면
				if(sum[record[i].r] - sum[record[i].l - 1] != record[i].s)
					return;
			}
			// 여기까지 왔다면 M개의 기록을 모두 만족하는 것
			// 이번 구성이 알려진 최대 햄스터 마리 수 보다 크다면 갱신
			if(sum[N] > max) {
				max = sum[N];
				for (int i = 1; i <= N; i++) {
					ans[i] = cage[i];
				}
			}
			return;
		}
		// 중복 순열
		for(int hamster = 0; hamster <= X; hamster++) {
			// idx번째 집에 hamster 만큼의 햄스터를 배치
			cage[idx] = hamster;
			sum[idx] = sum[idx - 1] + hamster;
			// 다음 우리에 햄스터 배치하러 이동
			solve(idx + 1);
		}
	}
}
