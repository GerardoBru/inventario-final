#!/usr/bin/env python3
from pathlib import Path
import time
import os

# Intentar con playwright sincrónico
try:
    from playwright.sync_api import sync_playwright
    
    doc_path = Path(r"c:\Users\chancro\Desktop\Inventario Final\doc")
    
    print("Iniciando Playwright...")
    with sync_playwright() as p:
        print("Lanzando navegador...")
        browser = p.chromium.launch(headless=True)
        
        # Diagrama de Clases
        print("Capturando Diagrama de Clases...")
        page = browser.new_page(viewport={'width': 1600, 'height': 1000})
        html_clases = (doc_path / "diagrama_clases_simple.html").as_posix()
        page.goto(f"file:///{html_clases.replace(' ', '%20')}")
        page.wait_for_timeout(4000)
        page.screenshot(path=str(doc_path / "DIAGRAMA_CLASES_SIMPLE.png"), full_page=False)
        print("✓ DIAGRAMA_CLASES_SIMPLE.png creado")
        page.close()
        
        # Diagrama de Casos de Uso
        print("Capturando Diagrama de Casos de Uso...")
        page = browser.new_page(viewport={'width': 1600, 'height': 1000})
        html_casos = (doc_path / "diagrama_casos_uso_simple.html").as_posix()
        page.goto(f"file:///{html_casos.replace(' ', '%20')}")
        page.wait_for_timeout(4000)
        page.screenshot(path=str(doc_path / "DIAGRAMA_CASOS_USO_SIMPLE.png"), full_page=False)
        print("✓ DIAGRAMA_CASOS_USO_SIMPLE.png creado")
        page.close()
        
        browser.close()
        print(f"\n✓ Diagramas generados en: {doc_path}")
        
except Exception as e:
    print(f"Error: {e}")
    import traceback
    traceback.print_exc()
