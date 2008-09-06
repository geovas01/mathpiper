;v1.0

PrntMess equ 100Ch
OutHex equ 1012h
DgtToBin equ 1015h
GetCharW equ 1009h
;Program entry point

	org 0200h
Main *
	lda #00000001b
	sta shadow
	
Top *
	ldy #7d
LeftLoop *
	clc
	rol shadow
	lda shadow
	sta 4001h
	jsr Delay
	dey
	bne LeftLoop
	
	ldy #7d
RightLoop *
	clc
	ror shadow
	lda shadow
	sta 4001h
	jsr Delay
	dey
	bne RightLoop
	
	jmp Top
	
Done *
	brk
	
Delay *
;Save registers on the stack.
	pha
	txa
	pha
	tya
	pha
;Change the number that is being loaded into the
; 'A' register in order to change the delay time.
	lda #10h
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
	
;Variables

shadow dbt 0d
	
	end

