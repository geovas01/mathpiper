;Program Name: blink.asm.
;
;Version: 1.1.
;
;Description: The purpose of this program is to blink
; the lights on and off continuously.
;

;**************************************
;      Program entry point.
;**************************************
	org 0200h

Main *
;Turn all the lights on and then waste some time
; so that the user can see the lights on.
	lda #11111111b
	sta 4001h
	jsr delay
	
;Turn all the lights off and then waste some time
; so that the user can see the lights off.
	lda #00000000b
	sta 4001h
	jsr delay
	
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

	end
	
