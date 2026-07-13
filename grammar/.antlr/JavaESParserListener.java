// Generated from c:/Users/alexp/Documents/GitHub/JavaES/grammar/JavaESParser.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link JavaESParser}.
 */
public interface JavaESParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link JavaESParser#programa}.
	 * @param ctx the parse tree
	 */
	void enterPrograma(JavaESParser.ProgramaContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#programa}.
	 * @param ctx the parse tree
	 */
	void exitPrograma(JavaESParser.ProgramaContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#elementoGlobal}.
	 * @param ctx the parse tree
	 */
	void enterElementoGlobal(JavaESParser.ElementoGlobalContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#elementoGlobal}.
	 * @param ctx the parse tree
	 */
	void exitElementoGlobal(JavaESParser.ElementoGlobalContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#paqueteDecl}.
	 * @param ctx the parse tree
	 */
	void enterPaqueteDecl(JavaESParser.PaqueteDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#paqueteDecl}.
	 * @param ctx the parse tree
	 */
	void exitPaqueteDecl(JavaESParser.PaqueteDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#importacionDecl}.
	 * @param ctx the parse tree
	 */
	void enterImportacionDecl(JavaESParser.ImportacionDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#importacionDecl}.
	 * @param ctx the parse tree
	 */
	void exitImportacionDecl(JavaESParser.ImportacionDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#rutaCalificada}.
	 * @param ctx the parse tree
	 */
	void enterRutaCalificada(JavaESParser.RutaCalificadaContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#rutaCalificada}.
	 * @param ctx the parse tree
	 */
	void exitRutaCalificada(JavaESParser.RutaCalificadaContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#claseDecl}.
	 * @param ctx the parse tree
	 */
	void enterClaseDecl(JavaESParser.ClaseDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#claseDecl}.
	 * @param ctx the parse tree
	 */
	void exitClaseDecl(JavaESParser.ClaseDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#miembroClase}.
	 * @param ctx the parse tree
	 */
	void enterMiembroClase(JavaESParser.MiembroClaseContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#miembroClase}.
	 * @param ctx the parse tree
	 */
	void exitMiembroClase(JavaESParser.MiembroClaseContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#declaracionGlobal}.
	 * @param ctx the parse tree
	 */
	void enterDeclaracionGlobal(JavaESParser.DeclaracionGlobalContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#declaracionGlobal}.
	 * @param ctx the parse tree
	 */
	void exitDeclaracionGlobal(JavaESParser.DeclaracionGlobalContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#declaracionVariable}.
	 * @param ctx the parse tree
	 */
	void enterDeclaracionVariable(JavaESParser.DeclaracionVariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#declaracionVariable}.
	 * @param ctx the parse tree
	 */
	void exitDeclaracionVariable(JavaESParser.DeclaracionVariableContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#variablesDeclaradas}.
	 * @param ctx the parse tree
	 */
	void enterVariablesDeclaradas(JavaESParser.VariablesDeclaradasContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#variablesDeclaradas}.
	 * @param ctx the parse tree
	 */
	void exitVariablesDeclaradas(JavaESParser.VariablesDeclaradasContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#variableDeclarada}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclarada(JavaESParser.VariableDeclaradaContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#variableDeclarada}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclarada(JavaESParser.VariableDeclaradaContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#metodoDecl}.
	 * @param ctx the parse tree
	 */
	void enterMetodoDecl(JavaESParser.MetodoDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#metodoDecl}.
	 * @param ctx the parse tree
	 */
	void exitMetodoDecl(JavaESParser.MetodoDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#modificadores}.
	 * @param ctx the parse tree
	 */
	void enterModificadores(JavaESParser.ModificadoresContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#modificadores}.
	 * @param ctx the parse tree
	 */
	void exitModificadores(JavaESParser.ModificadoresContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#modificador}.
	 * @param ctx the parse tree
	 */
	void enterModificador(JavaESParser.ModificadorContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#modificador}.
	 * @param ctx the parse tree
	 */
	void exitModificador(JavaESParser.ModificadorContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#parametros}.
	 * @param ctx the parse tree
	 */
	void enterParametros(JavaESParser.ParametrosContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#parametros}.
	 * @param ctx the parse tree
	 */
	void exitParametros(JavaESParser.ParametrosContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#parametro}.
	 * @param ctx the parse tree
	 */
	void enterParametro(JavaESParser.ParametroContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#parametro}.
	 * @param ctx the parse tree
	 */
	void exitParametro(JavaESParser.ParametroContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#tipo}.
	 * @param ctx the parse tree
	 */
	void enterTipo(JavaESParser.TipoContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#tipo}.
	 * @param ctx the parse tree
	 */
	void exitTipo(JavaESParser.TipoContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#tipoPrimitivo}.
	 * @param ctx the parse tree
	 */
	void enterTipoPrimitivo(JavaESParser.TipoPrimitivoContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#tipoPrimitivo}.
	 * @param ctx the parse tree
	 */
	void exitTipoPrimitivo(JavaESParser.TipoPrimitivoContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#bloque}.
	 * @param ctx the parse tree
	 */
	void enterBloque(JavaESParser.BloqueContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#bloque}.
	 * @param ctx the parse tree
	 */
	void exitBloque(JavaESParser.BloqueContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#instruccion}.
	 * @param ctx the parse tree
	 */
	void enterInstruccion(JavaESParser.InstruccionContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#instruccion}.
	 * @param ctx the parse tree
	 */
	void exitInstruccion(JavaESParser.InstruccionContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#varLocalDecl}.
	 * @param ctx the parse tree
	 */
	void enterVarLocalDecl(JavaESParser.VarLocalDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#varLocalDecl}.
	 * @param ctx the parse tree
	 */
	void exitVarLocalDecl(JavaESParser.VarLocalDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#instruccion_if}.
	 * @param ctx the parse tree
	 */
	void enterInstruccion_if(JavaESParser.Instruccion_ifContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#instruccion_if}.
	 * @param ctx the parse tree
	 */
	void exitInstruccion_if(JavaESParser.Instruccion_ifContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#instruccion_while}.
	 * @param ctx the parse tree
	 */
	void enterInstruccion_while(JavaESParser.Instruccion_whileContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#instruccion_while}.
	 * @param ctx the parse tree
	 */
	void exitInstruccion_while(JavaESParser.Instruccion_whileContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#instruccion_for}.
	 * @param ctx the parse tree
	 */
	void enterInstruccion_for(JavaESParser.Instruccion_forContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#instruccion_for}.
	 * @param ctx the parse tree
	 */
	void exitInstruccion_for(JavaESParser.Instruccion_forContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#forInicializacion}.
	 * @param ctx the parse tree
	 */
	void enterForInicializacion(JavaESParser.ForInicializacionContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#forInicializacion}.
	 * @param ctx the parse tree
	 */
	void exitForInicializacion(JavaESParser.ForInicializacionContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#declaracionFor}.
	 * @param ctx the parse tree
	 */
	void enterDeclaracionFor(JavaESParser.DeclaracionForContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#declaracionFor}.
	 * @param ctx the parse tree
	 */
	void exitDeclaracionFor(JavaESParser.DeclaracionForContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#listaExpresiones}.
	 * @param ctx the parse tree
	 */
	void enterListaExpresiones(JavaESParser.ListaExpresionesContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#listaExpresiones}.
	 * @param ctx the parse tree
	 */
	void exitListaExpresiones(JavaESParser.ListaExpresionesContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#instruccion_doWhile}.
	 * @param ctx the parse tree
	 */
	void enterInstruccion_doWhile(JavaESParser.Instruccion_doWhileContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#instruccion_doWhile}.
	 * @param ctx the parse tree
	 */
	void exitInstruccion_doWhile(JavaESParser.Instruccion_doWhileContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#instruccion_switch}.
	 * @param ctx the parse tree
	 */
	void enterInstruccion_switch(JavaESParser.Instruccion_switchContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#instruccion_switch}.
	 * @param ctx the parse tree
	 */
	void exitInstruccion_switch(JavaESParser.Instruccion_switchContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#casoSwitch}.
	 * @param ctx the parse tree
	 */
	void enterCasoSwitch(JavaESParser.CasoSwitchContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#casoSwitch}.
	 * @param ctx the parse tree
	 */
	void exitCasoSwitch(JavaESParser.CasoSwitchContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#defectoSwitch}.
	 * @param ctx the parse tree
	 */
	void enterDefectoSwitch(JavaESParser.DefectoSwitchContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#defectoSwitch}.
	 * @param ctx the parse tree
	 */
	void exitDefectoSwitch(JavaESParser.DefectoSwitchContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#instruccion_tryCatch}.
	 * @param ctx the parse tree
	 */
	void enterInstruccion_tryCatch(JavaESParser.Instruccion_tryCatchContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#instruccion_tryCatch}.
	 * @param ctx the parse tree
	 */
	void exitInstruccion_tryCatch(JavaESParser.Instruccion_tryCatchContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#capturarSecuencias}.
	 * @param ctx the parse tree
	 */
	void enterCapturarSecuencias(JavaESParser.CapturarSecuenciasContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#capturarSecuencias}.
	 * @param ctx the parse tree
	 */
	void exitCapturarSecuencias(JavaESParser.CapturarSecuenciasContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#capturarSecuencia}.
	 * @param ctx the parse tree
	 */
	void enterCapturarSecuencia(JavaESParser.CapturarSecuenciaContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#capturarSecuencia}.
	 * @param ctx the parse tree
	 */
	void exitCapturarSecuencia(JavaESParser.CapturarSecuenciaContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#finalmenteSecuencia}.
	 * @param ctx the parse tree
	 */
	void enterFinalmenteSecuencia(JavaESParser.FinalmenteSecuenciaContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#finalmenteSecuencia}.
	 * @param ctx the parse tree
	 */
	void exitFinalmenteSecuencia(JavaESParser.FinalmenteSecuenciaContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#instruccion_return}.
	 * @param ctx the parse tree
	 */
	void enterInstruccion_return(JavaESParser.Instruccion_returnContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#instruccion_return}.
	 * @param ctx the parse tree
	 */
	void exitInstruccion_return(JavaESParser.Instruccion_returnContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#instruccion_break}.
	 * @param ctx the parse tree
	 */
	void enterInstruccion_break(JavaESParser.Instruccion_breakContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#instruccion_break}.
	 * @param ctx the parse tree
	 */
	void exitInstruccion_break(JavaESParser.Instruccion_breakContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#instruccion_continue}.
	 * @param ctx the parse tree
	 */
	void enterInstruccion_continue(JavaESParser.Instruccion_continueContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#instruccion_continue}.
	 * @param ctx the parse tree
	 */
	void exitInstruccion_continue(JavaESParser.Instruccion_continueContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#instruccion_simple}.
	 * @param ctx the parse tree
	 */
	void enterInstruccion_simple(JavaESParser.Instruccion_simpleContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#instruccion_simple}.
	 * @param ctx the parse tree
	 */
	void exitInstruccion_simple(JavaESParser.Instruccion_simpleContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#expresion}.
	 * @param ctx the parse tree
	 */
	void enterExpresion(JavaESParser.ExpresionContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#expresion}.
	 * @param ctx the parse tree
	 */
	void exitExpresion(JavaESParser.ExpresionContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#asignacion}.
	 * @param ctx the parse tree
	 */
	void enterAsignacion(JavaESParser.AsignacionContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#asignacion}.
	 * @param ctx the parse tree
	 */
	void exitAsignacion(JavaESParser.AsignacionContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#operadorAsignacion}.
	 * @param ctx the parse tree
	 */
	void enterOperadorAsignacion(JavaESParser.OperadorAsignacionContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#operadorAsignacion}.
	 * @param ctx the parse tree
	 */
	void exitOperadorAsignacion(JavaESParser.OperadorAsignacionContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#logicoOr}.
	 * @param ctx the parse tree
	 */
	void enterLogicoOr(JavaESParser.LogicoOrContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#logicoOr}.
	 * @param ctx the parse tree
	 */
	void exitLogicoOr(JavaESParser.LogicoOrContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#logicoAnd}.
	 * @param ctx the parse tree
	 */
	void enterLogicoAnd(JavaESParser.LogicoAndContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#logicoAnd}.
	 * @param ctx the parse tree
	 */
	void exitLogicoAnd(JavaESParser.LogicoAndContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#igualdad}.
	 * @param ctx the parse tree
	 */
	void enterIgualdad(JavaESParser.IgualdadContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#igualdad}.
	 * @param ctx the parse tree
	 */
	void exitIgualdad(JavaESParser.IgualdadContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#relacion}.
	 * @param ctx the parse tree
	 */
	void enterRelacion(JavaESParser.RelacionContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#relacion}.
	 * @param ctx the parse tree
	 */
	void exitRelacion(JavaESParser.RelacionContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#suma}.
	 * @param ctx the parse tree
	 */
	void enterSuma(JavaESParser.SumaContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#suma}.
	 * @param ctx the parse tree
	 */
	void exitSuma(JavaESParser.SumaContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#producto}.
	 * @param ctx the parse tree
	 */
	void enterProducto(JavaESParser.ProductoContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#producto}.
	 * @param ctx the parse tree
	 */
	void exitProducto(JavaESParser.ProductoContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#unaria}.
	 * @param ctx the parse tree
	 */
	void enterUnaria(JavaESParser.UnariaContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#unaria}.
	 * @param ctx the parse tree
	 */
	void exitUnaria(JavaESParser.UnariaContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#postfijo}.
	 * @param ctx the parse tree
	 */
	void enterPostfijo(JavaESParser.PostfijoContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#postfijo}.
	 * @param ctx the parse tree
	 */
	void exitPostfijo(JavaESParser.PostfijoContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#sufijo}.
	 * @param ctx the parse tree
	 */
	void enterSufijo(JavaESParser.SufijoContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#sufijo}.
	 * @param ctx the parse tree
	 */
	void exitSufijo(JavaESParser.SufijoContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#primaria}.
	 * @param ctx the parse tree
	 */
	void enterPrimaria(JavaESParser.PrimariaContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#primaria}.
	 * @param ctx the parse tree
	 */
	void exitPrimaria(JavaESParser.PrimariaContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(JavaESParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(JavaESParser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#invocable}.
	 * @param ctx the parse tree
	 */
	void enterInvocable(JavaESParser.InvocableContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#invocable}.
	 * @param ctx the parse tree
	 */
	void exitInvocable(JavaESParser.InvocableContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#nuevaInstancia}.
	 * @param ctx the parse tree
	 */
	void enterNuevaInstancia(JavaESParser.NuevaInstanciaContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#nuevaInstancia}.
	 * @param ctx the parse tree
	 */
	void exitNuevaInstancia(JavaESParser.NuevaInstanciaContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaESParser#argumentos}.
	 * @param ctx the parse tree
	 */
	void enterArgumentos(JavaESParser.ArgumentosContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaESParser#argumentos}.
	 * @param ctx the parse tree
	 */
	void exitArgumentos(JavaESParser.ArgumentosContext ctx);
}