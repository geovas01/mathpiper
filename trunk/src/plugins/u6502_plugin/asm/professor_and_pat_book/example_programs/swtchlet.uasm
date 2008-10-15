;Program Name: swtchlet.asm.
;
;Version: 1.1.
;
;Description: The purpose of this program is to output a different
; letter of the alphabet depending on which switch was pressed last.
;

;************************************************************
;              Monitor Utility Subroutine Jump Table.
;************************************************************
OutChar   equ 1003h ;Output byte in A register to serial port.
                     
GetChar   equ 1006h ;Get a byte from the serial port.
                     
GetCharW  equ 1009h ;Wait and get a byte from the serial port.
                     
PrntMess  equ 100Ch ;Print a message to the serial port.
                     
OutSpace  equ 100Fh ;Output spaces to the serial port.                                 
                     
OutHex    equ 1012h ;Output a HEX number to the serial port.
                     
DgtToBin  equ 1015h ;Convert an ASCII digit into binary.
                     
GetLine   equ 1018h ;Input a line from the serial port.


;**************************************
;      Program entry point.
;**************************************
	org 0200h

Main *
	
CkSw0 *
	lda 4000h
	and #00000001b
	bne CkSw1
	lda #'A'
	sta LastLetter
	jmp OutLetter
	
CkSw1 *
	lda 4000h
	and #00000010b
	bne CkSw2
	lda #'B'
	sta LastLetter
	jmp OutLetter
	
CkSw2 *
	lda 4000h
	and #00000100b
	bne CkSw3
	lda #'C'
	sta LastLetter
	jmp OutLetter
	
CkSw3 *
	lda 4000h
	and #00001000b
	bne CkSw4
	lda #'D'
	sta LastLetter
	jmp OutLetter
	
CkSw4 *
	lda 4000h
	and #00010000b
	bne CkSw5
	lda #'E'
	sta LastLetter
	jmp OutLetter
	
CkSw5 *
	lda 4000h
	and #00100000b
	bne CkSw6
	lda #'F'
	sta LastLetter
	jmp OutLetter
	
CkSw6 *
	lda 4000h
	and #01000000b
	bne NoSwitch
	lda #'G'
	sta LastLetter
	jmp OutLetter
	
NoSwitch *

	
OutLetter *
	lda LastLetter
	jsr OutChar
	jsr Delay
	jmp Main
	
	
;Exit the program.
	brk
	
	
;**************************************
;        Subroutines area.
;**************************************

;**************************************
;Delay subroutine.
;
;The purpose of this subroutine is to generate
; a delay so that the rate of the blinking
; can be controlled.
;
;Change the number that is being loaded into
; the 'A' register to change the delay time.
;**************************************
Delay *
;Save registers on the stack.
	pha
	txa
	pha
	tya
	pha
	
;Change the number that is being loaded into the
; 'A' register in order to change the delay time.
	lda #010h
	
OutLoop *
	ldx #0ffh
	
InLoop1 *
	ldy #0ffh
	
InLoop2 *
	dey
	bne InLoop2
	
	dex
	bne InLoop1
	
	sec
	sbc #1d
	bne OutLoop
	
;Restore registers from the stack.
	pla
	tay
	pla
	tax
	pla

	rts
	

;**************************************
;        Variables area.
;**************************************
LastLetter dbt "*"
	end
	
