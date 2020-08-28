import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * SWEA 3234 : 준환이의 양팔저울(D4)
 */
public class SWEA3234 {
	static int N;
	
	static int[] weight;
	
	static int answer;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		
		for(int tc = 1; tc <= T; tc++) {
			answer = 0;
			
			N = Integer.parseInt(br.readLine());
			
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			weight = new int[N];
			int totalWeight = 0;
			for(int i = 0; i < N; i++) {
				weight[i] = Integer.parseInt(st.nextToken());
				totalWeight += weight[i];
			}
			
			solve(0, 0, 0, totalWeight, 0);
			
			System.out.format("#%d %d\n", tc, answer);
		}
		br.close();
	}

	public static void solve(int cnt, int rightSum, int leftSum, int remain, int visited) {
		if(leftSum >= rightSum + remain) {
			int caseNum = 1 << (N - cnt);
			
			for(int i = 1; i <= N - cnt; i++)
				caseNum *= i;
			
			answer += caseNum;
			return;
		}
		if(cnt == N) {
			answer++;
			return;
		}
		
		for(int i = 0; i < N; i++) {
			if((visited & (1 << i)) > 0)
				continue;
			
			solve(cnt + 1, rightSum, leftSum + weight[i], remain - weight[i], visited | (1 << i));
			
			if(rightSum + weight[i] > leftSum)
				continue;
			
			solve(cnt + 1, rightSum + weight[i], leftSum, remain - weight[i], visited | (1 << i));
		}
	}
}
