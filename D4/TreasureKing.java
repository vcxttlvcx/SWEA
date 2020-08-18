import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * SWEA 7829. 보물왕 태혁
 * 종이에 써져 있는 약수를 보고 원래의 숫자를 만들어 내기
 * ## 결과 제출 시 클래스 이름을 Solution으로 바꿀 것
 */

public class TreasureKing {
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
//			약수의 개수
			int P = sc.nextInt();
//			약수를 저장할 배열 선언
			int[] measure = new int[P];
//			약수를 저장
			for(int i = 0; i < P; i++)
				measure[i] = sc.nextInt();
//			오름 차순으로 정렬
			Arrays.sort(measure);
//			약수의 개수가 짝수이면 가운대에서 양옆의 값 두개를 곱하고, 홀수 이면 가운대 값을 제곱하면 원래의 수가 나온다
			if(P % 2 == 0)
				ans = measure[P / 2 - 1] * measure[P / 2];
			else
				ans = measure[P / 2] * measure[P / 2];
//			최종 결과 출력
			sb.append(ans);
			System.out.println(sb.toString());
		}	// end test_case
		
		sc.close();
	}
}
