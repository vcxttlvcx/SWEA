import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * SWEA 1486. 장훈이의 높은 선반
 * 점원들이 탑을 만들어 탑의 높이가 B 이상인 탑 중에서 높이가 가장 낮은 탑을 알아내보자.
 * 부분집합을 이용한 문제
 * ## 결과 제출 시 클래스 이름을 Solution으로 바꿀 것
 */

public class HighShelf {
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
		T = sc.nextInt();

		for(int test_case = 1; test_case <= T; test_case++) {
//			최종 출력을 위한 StringBuilder의 선언와 출력 양식 초기화
			StringBuilder sb = new StringBuilder();
			sb.append("#").append(test_case).append(" ");
//			결과 저장할 변수 선언
			int ans = 0;
//			나올 수 있는 최대 값 저장하기 위한 변수 선언
			int max = 0;
//			점원의 수
			int N = sc.nextInt();
//			선반의 높이
			int B = sc.nextInt();
//			선원의 키를 저장할 배열 선언 및 초기화
			int[] staff = new int[N];
			for(int i = 0; i < N; i++) {
				staff[i] = sc.nextInt();
				max += staff[i];	// 나올 수 있는 최대값 계산
			}
//			나올 수 있는 최대값으로 결과 초기화
			ans = max;
//			비트 마스킹을 이용한 풀이
			for(int i = 1; i < (1 << N); i++) {
				int height = 0;
				for(int j = 0; j < N; j++) {
//					부분 집합에 속하는 원소를 찾았을 때
					if((i & (1 << j)) > 0) {
						height += staff[j];
//						지끔 까지의 높이 값이 이미 찾은 결과 값보다 큰경우 for문 탈출
						if(height > ans)
							break;
					}
				}
//				높이의 합이 B보다 클경우 앞서 찾은 값보다 낮은 값인지 확인
				if(height >= B)
					ans = height < ans ? height : ans;
			}
//			높이 B와의 차이값으로 결과값 생성
			ans = ans - B;
//			최종 결과 출력
			sb.append(ans);
			System.out.println(sb.toString());
		}	// end test_case
		
		sc.close();
	}
}
