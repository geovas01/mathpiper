INPUTS	    .equ $0800
OUTPUTS	    .equ $0801
LCDWRITE    .equ $0802
L000F0      .equ $00F0
L08001      .equ $8001
            
.org $0200
		jmp Main
		
.org $0205
IntVec
		inx 
		stx OUTPUTS
		rti
		
Main		
		jsr Hello6502	
		jmp pollInputs

Hello6502
	    lda #'H'
	    sta LCDWRITE
	    lda #'e'
	    sta LCDWRITE
	    lda #'l'
	    sta LCDWRITE
	    lda #'l'
	    sta LCDWRITE
	    lda #'o'
	    sta LCDWRITE
	    lda #'6'
	    sta LCDWRITE
	    lda #'5'
	    sta LCDWRITE
	    lda #'0'
	    sta LCDWRITE
	    lda #'2'
	    sta LCDWRITE
	    lda #$0A
	    sta LCDWRITE
	    lda #$00
	    rts
     
pollInputs
		;lda INPUTS
		;sta OUTPUTS
		jmp pollInputs
 
 
            .end
