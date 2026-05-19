import subprocess
import sys
from pathlib import Path

# Instalar playwright si no está instalado
try:
    from playwright.sync_api import sync_playwright
except ImportError:
    print("Instalando Playwright...")
    subprocess.check_call([sys.executable, "-m", "pip", "install", "playwright", "-q"])
    subprocess.check_call([sys.executable, "-m", "playwright", "install", "chromium", "-q"])

from playwright.sync_api import sync_playwright

def screenshot_html_to_png(html_file, output_file):
    with sync_playwright() as p:
        browser = p.chromium.launch()
        page = browser.new_page(viewport={'width': 1920, 'height': 1080})
        file_path = Path(html_file).absolute()
        page.goto(f"file:///{file_path}")
        page.wait_for_timeout(3000)
        page.screenshot(path=output_file)
        browser.close()
        print(f"✓ Guardado: {output_file}")

# Convertir diagramas
doc_path = Path("c:\\Users\\chancro\\Desktop\\Inventario Final\\doc")

html_clases = doc_path / "diagrama_clases_simple.html"
output_clases = doc_path / "DIAGRAMA_CLASES_SIMPLE.png"

html_casos = doc_path / "diagrama_casos_uso_simple.html"
output_casos = doc_path / "DIAGRAMA_CASOS_USO_SIMPLE.png"

print("Generando diagramas PNG...")
screenshot_html_to_png(str(html_clases), str(output_clases))
screenshot_html_to_png(str(html_casos), str(output_casos))

print("\n✓ ¡Diagramas en PNG creados exitosamente!")
print(f"✓ Ubicación: {doc_path}")
