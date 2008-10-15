INPUT	    	equ 4000h
OUTPUT	    	equ 4001h
LCDWRITE    	equ 4002h

	org 0200h
	
	lda #'A'
	sta LCDWRITE
	lda #0ah
	sta LCDWRITE
	
	lda #IRQHndlr<		
	sta 0002h
	lda #IRQHndlr>
	sta 0003h
	
	ldx #'A'
	
	lda #0d
	sta counter
	
	cli
	
top *
	jmp top
	
	
IRQHndlr *
	sei
	
	inx
	 
	cpx #91d
	bne NotZ
	
IsZ *
	ldx #'A'
	
NotZ *
	
	stx LCDWRITE
	
	inc counter
	ldy counter
	cpy #5d
	bne NotFive
	
IsFive *
	ldy #0d
	sty counter
	ldy #0ah
	sty LCDWRITE
	
NotFive *
	
	cli
	rti

counter dbt ?
	end
	