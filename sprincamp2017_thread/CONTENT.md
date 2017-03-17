# 목차
## in the beginning was the bit.
- 0, 1
  - 컴퓨터는 무한한 0과 1의 반복
- 8 bit CPU
  - 컴퓨터가 0과 1을 8bit씩 읽는다.
  - ALU register: 명령어(instruction) opcode(operation code)
    - 00000000 - NONE
    - 00000001 - MOV - 레지스터에 값을 할당   - 인자 1개만 원함.
    - 00000002 - ADD - 더하기              - 인자 2개를 원함. 
    - 00000003 - SUB - 빼기
    - 00000004 - MUL - 곱하기
    - 00000005 - DIV - 나누기
    - 예제 
      - 2 + 3
        - 00000002 00000010 00000011
      - 5 * 7
        - 00000004 00000101 00000111
  - [8bit emulator](https://boojongmin.github.io/assembler-simulator/index.html)
  - REGISTER - 계산한 값을 저장
    - 8bit momory
    - A, B, C, D
      - example
        - A = 1 + 2
          - ??? => 계산한 결과값을 저장할 곳이 없다!!!
          - MOV A, 00000001
          - ADD A, 00000010
          - 이런 느낌?
          ```java
            int a = 1;
            int b = 2;
            //swap
            int tmp = a;
            int a = b;
            int b = tmp;
          ```
    - FLAG
      - CMP A, B
        - A reg == B reg then true
    - ![8 bit opcode](./images/8bit_table.jpg)
    - [x64 opcode list](http://ref.x86asm.net/coder64.html)
  - RAM
    - 주기억장치
    - Adress
    - 2^8 = 256
    - MOV [10], 7
    - MOV A, 10; MOV [A], 7
    ```assembly
      MOV [224], 17
      MOV [225], 17
      MOV [226], 17
      MOV [227], 17
      MOV [228], 17
      MOV [229], 17
      MOV [230], 17
      MOV [231], 17
      MOV [232], 65
      MOV A, 232
      MOV [A], 97
      MOV [A+1], 98
      MOV [A+2], 99
      MOV [A+3], 100
      MOV [A+4], 101
      MOV [A+5], 102
      MOV [A+6], 103
    ```
    ```assembly
      MOV A, 224
      MOV [A], 17
      INC A
      MOV [A], 17
      INC A
      MOV [A], 17
      INC A
      MOV [A], 17
      INC A
      MOV [A], 17
      INC A
      MOV [A], 17
      INC A
      MOV [A], 17
      INC A
      MOV [A], 17
      INC A
      MOV A, 232
      MOV [A], 97
      INC A
      MOV [A], 98
      INC A
      MOV [A], 99
      INC A
      MOV [A], 100
      INC A
      MOV [A], 101
      INC A
      MOV [A], 102
      INC A
      MOV [A], 103
    ```
    ```assembly
      MOV A, 224
      MOV B, 232
      .loop1: 
        MOV [A], 17
        INC A
        CMP A, B
        JNZ .loop1
      MOV A, 232
      MOV B, 97
      .loop2:
        MOV [A], B
        INC A
        INC B
        CMP B, 103
        JNZ .loop2

            HLT 
    ```
  - HDD
    - program, process
    - 보조기억장치
    - 속도: register > ram > hdd 
    ```assembly
        JMP start
      hello: 
        DB "KSUG"
        DB 0	
      start:
        MOV A, 1           
        MOV B, 2	
        ADD B, A
        MOV C, hello
        MOV D, 232
        CALL print   ;  JMP, CALL -> STACK 설명(method 실행 후 return -> 상태 복원)
              HLT      
      print:
        PUSH A
        PUSH B
        MOV B, 0
      .loop:
        MOV A, [C]
        MOV [D], A
        INC C
        INC D
        CMP B, [C]
        JNZ .loop 
        POP B
        POP A
      RET     
    ```  
    - 




